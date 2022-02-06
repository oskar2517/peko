package me.oskar.peko.compiler;

import me.oskar.peko.ast.*;
import me.oskar.peko.ast.visitor.BaseVisitor;
import me.oskar.peko.code.Code;
import me.oskar.peko.code.OpCode;
import me.oskar.peko.compiler.constant.ConstantPool;
import me.oskar.peko.compiler.symbol.SymbolTable;
import me.oskar.peko.compiler.symbol.UserFunctionEntry;
import me.oskar.peko.std.BuiltInTable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CodeGeneratorVisitor extends BaseVisitor {

    private SymbolTable symbolTable;
    private final ConstantPool constantPool = new ConstantPool();
    private final ByteArrayOutputStream bytecode = new ByteArrayOutputStream();
    private DataOutputStream output = new DataOutputStream(bytecode);
    private int functionsPosition = 0;
    private int globalsCount = 0;

    public CodeGeneratorVisitor(final SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public byte[] getBytecode() throws IOException {
        final var fileBuffer = new ByteArrayOutputStream();
        final var fileOut = new DataOutputStream(fileBuffer);
        fileOut.write("peko".getBytes(StandardCharsets.UTF_8)); // Magic number
        fileOut.write(constantPool.toByteArray());
        fileOut.writeInt(globalsCount);
        fileOut.writeInt(functionsPosition);
        fileOut.write(bytecode.toByteArray());

        return fileBuffer.toByteArray();
    }

    @Override
    public void visit(final ArrayAccessNode arrayAccessNode) {
        arrayAccessNode.getTarget().accept(this);
        arrayAccessNode.getIndex().accept(this);

        emit(OpCode.LOAD_INDEX);
    }

    @Override
    public void visit(final ArrayAssignNode arrayAssignNode) {
        final var origOutput = output;

        final var indexBytes = new ByteArrayOutputStream();
        output = new DataOutputStream(indexBytes);
        arrayAssignNode.getTarget().accept(this);
        output = origOutput;

        final var targetBytesArray = indexBytes.toByteArray();
        writeToOut(Arrays.copyOfRange(targetBytesArray, 0, targetBytesArray.length - 1));

        arrayAssignNode.getValue().accept(this);

        emit(OpCode.STORE_INDEX);
    }

    @Override
    public void visit(final ArrayNode arrayNode) {
        for (final var v : arrayNode.getValue()) {
            v.accept(this);
        }

        emit(OpCode.CONSTRUCT_ARRAY, arrayNode.getValue().size());
    }

    @Override
    public void visit(final BinaryOperatorNode binaryOperatorNode) {
        switch (binaryOperatorNode.getType()) {
            case AND -> {
                binaryOperatorNode.getLeft().accept(this);

                final var origOutput = output;
                final var rightBytes = new ByteArrayOutputStream();
                output = new DataOutputStream(rightBytes);
                binaryOperatorNode.getRight().accept(this);
                output = origOutput;

                emit(OpCode.JUMP_IF_TRUE, rightBytes.size() + 5);
                writeToOut(rightBytes.toByteArray());
                emit(OpCode.JUMP, 5);
                emit(OpCode.CONSTANT, constantPool.addConstant(false));
            }
            case OR -> {
                binaryOperatorNode.getLeft().accept(this);

                final var origOutput = output;
                final var rightBytes = new ByteArrayOutputStream();
                output = new DataOutputStream(rightBytes);
                binaryOperatorNode.getRight().accept(this);
                output = origOutput;

                emit(OpCode.JUMP_IF_FALSE, rightBytes.size() + 5);
                writeToOut(rightBytes.toByteArray());
                emit(OpCode.JUMP, 5);
                emit(OpCode.CONSTANT, constantPool.addConstant(true));
            }
            case ADD -> {
                binaryOperatorNode.getLeft().accept(this);
                binaryOperatorNode.getRight().accept(this);
                emit(OpCode.ADD);
            }
            case SUB -> {
                binaryOperatorNode.getLeft().accept(this);
                binaryOperatorNode.getRight().accept(this);
                emit(OpCode.SUB);
            }
            case MUL -> {
                binaryOperatorNode.getLeft().accept(this);
                binaryOperatorNode.getRight().accept(this);
                emit(OpCode.MUL);
            }
            case DIV -> {
                binaryOperatorNode.getLeft().accept(this);
                binaryOperatorNode.getRight().accept(this);
                emit(OpCode.DIV);
            }
            case MOD -> {
                binaryOperatorNode.getLeft().accept(this);
                binaryOperatorNode.getRight().accept(this);
                emit(OpCode.REM);
            }
            case EQ -> {
                binaryOperatorNode.getLeft().accept(this);
                binaryOperatorNode.getRight().accept(this);
                emit(OpCode.EQUALS);
            }
            case NE -> {
                binaryOperatorNode.getLeft().accept(this);
                binaryOperatorNode.getRight().accept(this);
                emit(OpCode.NOT_EQUALS);
            }
            case LT -> {
                binaryOperatorNode.getLeft().accept(this);
                binaryOperatorNode.getRight().accept(this);
                emit(OpCode.LESS_THAN);
            }
            case LE -> {
                binaryOperatorNode.getLeft().accept(this);
                binaryOperatorNode.getRight().accept(this);
                emit(OpCode.LESS_THAN_OR_EQUAL);
            }
            case GT -> {
                binaryOperatorNode.getLeft().accept(this);
                binaryOperatorNode.getRight().accept(this);
                emit(OpCode.GREATER_THAN);
            }
            case GE -> {
                binaryOperatorNode.getLeft().accept(this);
                binaryOperatorNode.getRight().accept(this);
                emit(OpCode.GREATER_THAN_OR_EQUAL);
            }
        }
    }

    @Override
    public void visit(final BlockNode blockNode) {
        final var origSymbolTable = symbolTable;
        symbolTable = blockNode.getSymbolTable();

        for (Node n : blockNode.getBody()) {
            n.accept(this);
        }

        symbolTable = origSymbolTable;
    }

    @Override
    public void visit(final BooleanNode booleanNode) {
        final var index = constantPool.addConstant(booleanNode.getValue());
        emit(OpCode.CONSTANT, index);
    }

    @Override
    public void visit(final CallNode callNode) {
        if (BuiltInTable.getInstance().isBuiltInFunction(callNode.getFunctionName())) {
            final var functionIndex = BuiltInTable.getInstance().getBuiltInFunctionIndex(callNode.getFunctionName());

            for (Node a : callNode.getArguments()) {
                a.accept(this);
            }

            emit(OpCode.CALL_BUILTIN, functionIndex);
        } else {
            final var functionEntry = (UserFunctionEntry) symbolTable.lookup(callNode.getFunctionName()).getSymbolEntry();

            for (Node a : callNode.getArguments()) {
                a.accept(this);
            }

            emit(OpCode.ALLOCATE_STACK_FRAME, functionEntry.getSymbolTable().getSymbolCount());
            emit(OpCode.CALL, functionEntry.getPosition());
            emit(OpCode.RESET_STACK_FRAME);
            emit(OpCode.DROP, callNode.getArguments().size());
            emit(OpCode.LOAD_RETURN);
        }
    }

    @Override
    public void visit(final ExpressionStatementNode expressionStatementNode) {
        expressionStatementNode.getValue().accept(this);
        emit(OpCode.DROP, 1);
    }

    @Override
    public void visit(final FileNode fileNode) {
        globalsCount = fileNode.getVariables().size();

        for (FunctionNode f : fileNode.getFunctions()) {
            f.accept(this);
        }

        fileNode.getMainFunction().accept(this);

        functionsPosition = output.size();
        for (VariableDeclarationNode v : fileNode.getVariables()) {
            v.accept(this);
        }

        final var mainFunction = (UserFunctionEntry) symbolTable.lookup("main").getSymbolEntry();
        emit(OpCode.ALLOCATE_STACK_FRAME, mainFunction.getSymbolTable().getSymbolCount());
        emit(OpCode.CALL, mainFunction.getPosition());
        emit(OpCode.RESET_STACK_FRAME);
        emit(OpCode.HALT);
    }

    @Override
    public void visit(final FunctionNode functionNode) {
        final var functionEntry = (UserFunctionEntry) symbolTable.lookup(functionNode.getName()).getSymbolEntry();

        final var origSymbolTable = symbolTable;
        symbolTable = functionEntry.getSymbolTable();

        functionEntry.setPosition(output.size());

        functionNode.getBody().accept(this);

        emit(OpCode.CONSTANT, constantPool.addNilConstant());
        emit(OpCode.STORE_RETURN);
        emit(OpCode.RETURN);

        symbolTable = origSymbolTable;
    }

    @Override
    public void visit(final IdentNode identNode) {
        final var symbol = symbolTable.lookup(identNode.getValue());

        if (symbol.isGlobal()) {
            emit(OpCode.LOAD_GLOBAL, symbol.getIndex());
        } else {
            emit(OpCode.LOAD_LOCAL, symbol.getIndex());
        }
    }

    @Override
    public void visit(final IfNode ifNode) {
        ifNode.getCondition().accept(this);

        final var origOutput = output;
        final var consequenceBytes = new ByteArrayOutputStream();
        output = new DataOutputStream(consequenceBytes);
        ifNode.getConsequence().accept(this);
        output = origOutput;

        var skipSize = consequenceBytes.size();
        if (ifNode.getAlternative() != null) {
            // When if node has an alternative, an additional JMP instruction will be compiled at
            // the beginning of said block which also has to be skipped.
            skipSize += 5;
        }
        emit(OpCode.JUMP_IF_TRUE, skipSize);

        writeToOut(consequenceBytes.toByteArray());

        if (ifNode.getAlternative() != null) {
            final var alternativeBytes = new ByteArrayOutputStream();
            output = new DataOutputStream(alternativeBytes);
            ifNode.getAlternative().accept(this);
            output = origOutput;

            emit(OpCode.JUMP, alternativeBytes.size());

            writeToOut(alternativeBytes.toByteArray());
        }
    }

    @Override
    public void visit(final NilNode nilNode) {
        final var index =  constantPool.addNilConstant();
        emit(OpCode.CONSTANT, index);
    }

    @Override
    public void visit(final NumberNode numberNode) {
        final var index = constantPool.addConstant(numberNode.getValue());
        emit(OpCode.CONSTANT, index);
    }

    @Override
    public void visit(final ReturnNode returnNode) {
        if (returnNode.getValue() != null) {
            returnNode.getValue().accept(this);
        } else {
            emit(OpCode.CONSTANT, constantPool.addNilConstant());
        }

        emit(OpCode.STORE_RETURN);
        emit(OpCode.RETURN);
    }

    @Override
    public void visit(final StringNode stringNode) {
        final var index = constantPool.addConstant(stringNode.getValue());
        emit(OpCode.CONSTANT, index);
    }

    @Override
    public void visit(final UnaryOperatorNode unaryOperatorNode) {
        unaryOperatorNode.getRight().accept(this);
        switch (unaryOperatorNode.getType()) {
            case NEG -> emit(OpCode.NEG);
            case NOT -> emit(OpCode.NOT);
        }
    }

    @Override
    public void visit(final VariableAssignNode variableAssignNode) {
        final var symbol = symbolTable.lookup(variableAssignNode.getName());

        variableAssignNode.getValue().accept(this);
        if (symbol.isGlobal()) {
            emit(OpCode.STORE_GLOBAL, symbol.getIndex());
        } else {
            emit(OpCode.STORE_LOCAL, symbol.getIndex());
        }
    }

    @Override
    public void visit(final VariableDeclarationNode variableDeclarationNode) {
        variableDeclarationNode.getValue().accept(this);

        final var symbol = symbolTable.lookup(variableDeclarationNode.getName());
        if (symbol.isGlobal()) {
            emit(OpCode.STORE_GLOBAL, symbol.getIndex());
        } else {
            emit(OpCode.STORE_LOCAL, symbol.getIndex());
        }
    }

    @Override
    public void visit(final WhileNode whileNode) {
        final var conditionPosition = output.size();
        whileNode.getCondition().accept(this);

        final var origOutput = output;
        final var bodyBytes = new ByteArrayOutputStream();
        output = new DataOutputStream(bodyBytes);
        whileNode.getBody().accept(this);
        output = origOutput;

        emit(OpCode.JUMP_IF_TRUE, bodyBytes.size() + 5);
        writeToOut(bodyBytes.toByteArray());
        emit(OpCode.JUMP, conditionPosition - output.size() - 5);
    }

    private void emit(final int op) {
        try {
            final var ins = Code.make(op);
            output.write(ins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void emit(final int op, final int immediate) {
        try {
            final var ins = Code.make(op, immediate);
            output.write(ins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToOut(final byte[] bytes) {
        try {
            output.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
