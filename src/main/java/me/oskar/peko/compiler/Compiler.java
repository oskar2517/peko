package me.oskar.peko.compiler;

import me.oskar.peko.ast.*;
import me.oskar.peko.code.Code;
import me.oskar.peko.code.OpCode;
import me.oskar.peko.compiler.analysis.SemanticAnalyser;
import me.oskar.peko.compiler.constant.ConstantPool;
import me.oskar.peko.compiler.symbol.BuiltInFunctionEntry;
import me.oskar.peko.compiler.symbol.SymbolTable;
import me.oskar.peko.compiler.symbol.UserFunctionEntry;
import me.oskar.peko.std.BuiltInTable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Compiler {

    private final FileNode ast;
    private final ConstantPool constantPool = new ConstantPool();
    private SymbolTable symbolTable = new SymbolTable();
    private int globalsCount = 0;
    private int functionsPosition = 0;

    public Compiler(final FileNode ast) {
        this.ast = ast;

        initBuiltInFunctions();
        performSemanticAnalysis();
    }

    private void initBuiltInFunctions() {
        for (final var function : BuiltInTable.getInstance().getFunctions()) {
            symbolTable.enterBuiltIn(function.getName(), new BuiltInFunctionEntry(function.getParametersCount()));
        }
    }

    private void performSemanticAnalysis() {
        final var semanticAnalyser = new SemanticAnalyser(symbolTable);
        semanticAnalyser.visit(ast);
    }

    public byte[] compile() throws IOException {
        final var bytecode = new ByteArrayOutputStream();
        final var bytecodeOut = new DataOutputStream(bytecode);

        globalsCount = ast.getVariables().size();
        compile(ast, bytecodeOut);

        return getBytecode(bytecode.toByteArray());
    }

    private byte[] getBytecode(final byte[] bytecode) throws IOException {
        final var fileBuffer = new ByteArrayOutputStream();
        final var fileOut = new DataOutputStream(fileBuffer);
        fileOut.writeInt(0xC0DE); // Magic number
        fileOut.write(constantPool.toByteArray());
        fileOut.writeInt(globalsCount);
        fileOut.writeInt(functionsPosition);
        fileOut.write(bytecode);

        return fileBuffer.toByteArray();
    }

    private void compile(final BlockNode node, final DataOutputStream out) {
        final var prevSymbolTable = symbolTable;
        symbolTable = node.getSymbolTable();

        for (Node n : node.getBody()) {
            compile(n, out);
        }

        symbolTable = prevSymbolTable;
    }

    private void compile(final FileNode node, final DataOutputStream out) {
        for (FunctionNode f : node.getFunctions()) {
            compile(f, out);
        }

        compile(node.getMainFunction(), out);

        functionsPosition = out.size();
        for (VariableDeclarationNode v : node.getVariables()) {
            compile(v, out);
        }

        final var mainFunction = (UserFunctionEntry) symbolTable.lookup("main").getSymbolEntry();
        emit(OpCode.ASF, mainFunction.getSymbolTable().getSymbolCount(), out);
        emit(OpCode.CALL, mainFunction.getPosition(), out);
        emit(OpCode.RSF, out);
        emit(OpCode.HALT, out);
    }

    private void compile(final NumberNode node, final DataOutputStream out) {
        final var index = constantPool.addConstant(node.getValue());
        emit(OpCode.CONST, index, out);
    }

    private void compile(final StringNode node, final DataOutputStream out) {
        final var index = constantPool.addConstant(node.getValue());
        emit(OpCode.CONST, index, out);
    }

    private void compile(final BooleanNode node, final DataOutputStream out) {
        final var index = constantPool.addConstant(node.getValue());
        emit(OpCode.CONST, index, out);
    }

    private void compile(final BinaryOperatorNode node, final DataOutputStream out) {
        switch (node.getType()) {
            case AND -> {
                compile(node.getLeft(), out);

                final var rightBytes = new ByteArrayOutputStream();
                final var rightOut = new DataOutputStream(rightBytes);
                compile(node.getRight(), rightOut);

                emit(OpCode.BRF, rightBytes.size() + 5, out);
                writeToOut(out, rightBytes.toByteArray());
                emit(OpCode.JMP, 5, out);
                emit(OpCode.CONST, constantPool.addConstant(false), out);
            }
            case OR -> {
                compile(node.getLeft(), out);

                final var rightBytes = new ByteArrayOutputStream();
                final var rightOut = new DataOutputStream(rightBytes);
                compile(node.getRight(), rightOut);

                emit(OpCode.BRT, rightBytes.size() + 5, out);
                writeToOut(out, rightBytes.toByteArray());
                emit(OpCode.JMP, 5, out);
                emit(OpCode.CONST, constantPool.addConstant(true), out);
            }
            case ADD -> {
                compile(node.getLeft(), out);
                compile(node.getRight(), out);
                emit(OpCode.ADD, out);
            }
            case SUB -> {
                compile(node.getLeft(), out);
                compile(node.getRight(), out);
                emit(OpCode.SUB, out);
            }
            case MUL -> {
                compile(node.getLeft(), out);
                compile(node.getRight(), out);
                emit(OpCode.MUL, out);
            }
            case DIV -> {
                compile(node.getLeft(), out);
                compile(node.getRight(), out);
                emit(OpCode.DIV, out);
            }
            case MOD -> {
                compile(node.getLeft(), out);
                compile(node.getRight(), out);
                emit(OpCode.MOD, out);
            }
            case EQ -> {
                compile(node.getLeft(), out);
                compile(node.getRight(), out);
                emit(OpCode.EQ, out);
            }
            case NE -> {
                compile(node.getLeft(), out);
                compile(node.getRight(), out);
                emit(OpCode.NE, out);
            }
            case LT -> {
                compile(node.getLeft(), out);
                compile(node.getRight(), out);
                emit(OpCode.LT, out);
            }
            case LE -> {
                compile(node.getLeft(), out);
                compile(node.getRight(), out);
                emit(OpCode.LE, out);
            }
            case GT -> {
                compile(node.getLeft(), out);
                compile(node.getRight(), out);
                emit(OpCode.GT, out);
            }
            case GE -> {
                compile(node.getLeft(), out);
                compile(node.getRight(), out);
                emit(OpCode.GE, out);
            }
        }
    }

    private void compile(final UnaryOperatorNode node, final DataOutputStream out) {
        compile(node.getRight(), out);
        switch (node.getType()) {
            case NEG -> emit(OpCode.NEG, out);
            case NOT -> emit(OpCode.NOT, out);
        }
    }

    private void compile(final IfNode node, final DataOutputStream out) {
        compile(node.getCondition(), out);

        final var consequenceBytes = new ByteArrayOutputStream();
        final var consequenceOut = new DataOutputStream(consequenceBytes);
        compile(node.getConsequence(), consequenceOut);

        var skipSize = consequenceBytes.size();
        if (node.getAlternative() != null) {
            // When if node has an alternative, an additional JMP instruction will be compiled at
            // the beginning of said block which also has to be skipped.
            skipSize += 5;
        }
        emit(OpCode.BRF, skipSize, out);

        writeToOut(out, consequenceBytes.toByteArray());

        if (node.getAlternative() != null) {
            final var alternativeBytes = new ByteArrayOutputStream();
            final var alternativeOut = new DataOutputStream(alternativeBytes);
            compile(node.getAlternative(), alternativeOut);

            emit(OpCode.JMP, alternativeOut.size(), out);

            writeToOut(out, alternativeBytes.toByteArray());
        }
    }

    private void compile(final WhileNode node, final DataOutputStream out) {
        final var conditionPosition = out.size();
        compile(node.getCondition(), out);

        final var bodyBytes = new ByteArrayOutputStream();
        final var bodyOut = new DataOutputStream(bodyBytes);
        compile(node.getBody(), bodyOut);

        emit(OpCode.BRF, bodyBytes.size() + 5, out);
        writeToOut(out, bodyBytes.toByteArray());
        emit(OpCode.JMP, conditionPosition - out.size() - 5, out);
    }

    private void compile(final VariableDeclarationNode node, final DataOutputStream out) {
        compile(node.getValue(), out);

        final var symbol = symbolTable.lookup(node.getName());
        if (symbol.isGlobal()) {
            emit(OpCode.STORE_G, symbol.getIndex(), out);
        } else {
            emit(OpCode.STORE_L, symbol.getIndex(), out);
        }
    }

    private void compile(final ArrayNode node, final DataOutputStream out) {
        for (final var v : node.getValue()) {
            compile(v, out);
        }

        emit(OpCode.ARRAY, node.getValue().size(), out);
    }

    private void compile(final ArrayAccessNode node, final DataOutputStream out) {
        compile(node.getTarget(), out);
        compile(node.getIndex(), out);

        emit(OpCode.LOAD_A, out);
    }

    private void compile(final ArrayAssignNode node, final DataOutputStream out) {
        final var indexBytes = new ByteArrayOutputStream();
        final var indexOut = new DataOutputStream(indexBytes);
        compile(node.getTarget(), indexOut);
        final var targetBytesArray = indexBytes.toByteArray();
        writeToOut(out, Arrays.copyOfRange(targetBytesArray, 0, targetBytesArray.length - 1));

        compile(node.getValue(), out);

        emit(OpCode.STORE_A, out);
    }

    private void compile(final VariableAssignNode node, final DataOutputStream out) {
        final var symbol = symbolTable.lookup(node.getName());

        compile(node.getValue(), out);
        if (symbol.isGlobal()) {
            emit(OpCode.STORE_G, symbol.getIndex(), out);
        } else {
            emit(OpCode.STORE_L, symbol.getIndex(), out);
        }
    }

    private void compile(final IdentNode node, final DataOutputStream out) {
        final var symbol = symbolTable.lookup(node.getValue());

        if (symbol.isGlobal()) {
            emit(OpCode.LOAD_G, symbol.getIndex(), out);
        } else {
            emit(OpCode.LOAD_L, symbol.getIndex(), out);
        }
    }

    private void compile(final FunctionNode node, final DataOutputStream out) {
        final var functionEntry = (UserFunctionEntry) symbolTable.lookup(node.getName()).getSymbolEntry();

        final var prevSymbolTable = symbolTable;
        symbolTable = functionEntry.getSymbolTable();

        functionEntry.setPosition(out.size());

        compile(node.getBody(), out);

        emit(OpCode.CONST, constantPool.addNilConstant(), out);
        emit(OpCode.STORE_R, out);
        emit(OpCode.RET, out);

        symbolTable = prevSymbolTable;
    }

    private void compile(final CallNode node, final DataOutputStream out) {
        if (BuiltInTable.getInstance().isBuiltInFunction(node.getFunctionName())) {
            final var functionIndex = BuiltInTable.getInstance().getBuiltInFunctionIndex(node.getFunctionName());

            for (Node a : node.getArguments()) {
                compile(a, out);
            }

            emit(OpCode.CALL_B, functionIndex, out);
        } else {
            final var functionEntry = (UserFunctionEntry) symbolTable.lookup(node.getFunctionName()).getSymbolEntry();

            for (Node a : node.getArguments()) {
                compile(a, out);
            }

            emit(OpCode.ASF, functionEntry.getSymbolTable().getSymbolCount(), out);
            emit(OpCode.CALL, functionEntry.getPosition(), out);
            emit(OpCode.RSF, out);
            emit(OpCode.POP, node.getArguments().size(), out);
            emit(OpCode.LOAD_R, out);
        }
    }

    private void compile(final ReturnNode node, final DataOutputStream out) {
        if (node.getValue() != null) {
            compile(node.getValue(), out);
        } else {
            emit(OpCode.CONST, constantPool.addNilConstant(), out);
        }

        emit(OpCode.STORE_R, out);
        emit(OpCode.RET, out);
    }

    private void compile(final ExpressionStatementNode node, final DataOutputStream out) {
        compile(node.getValue(), out);
        emit(OpCode.POP, 1, out);
    }

    private void compile(final NilNode node, final DataOutputStream out) {
        final var index =  constantPool.addNilConstant();
        emit(OpCode.CONST, index, out);
    }

    private void compile(final Node node, final DataOutputStream out) {
        if (node instanceof NumberNode n) {
            compile(n, out);
        } else if (node instanceof BooleanNode n) {
            compile(n, out);
        } else if (node instanceof StringNode n) {
            compile(n, out);
        } else if (node instanceof NilNode n) {
            compile(n, out);
        } else if (node instanceof BinaryOperatorNode n) {
            compile(n, out);
        } else if (node instanceof UnaryOperatorNode n) {
            compile(n, out);
        } else if (node instanceof IfNode n) {
            compile(n, out);
        } else if (node instanceof FunctionNode n) {
            compile(n, out);
        } else if (node instanceof FileNode n) {
            compile(n, out);
        } else if (node instanceof WhileNode n) {
            compile(n, out);
        } else if (node instanceof VariableDeclarationNode n) {
            compile(n, out);
        } else if (node instanceof VariableAssignNode n) {
            compile(n, out);
        } else if (node instanceof IdentNode n) {
            compile(n, out);
        } else if (node instanceof CallNode n) {
            compile(n, out);
        } else if (node instanceof ReturnNode n) {
            compile(n, out);
        } else if (node instanceof ExpressionStatementNode n) {
            compile(n, out);
        } else if (node instanceof ArrayNode n) {
            compile(n, out);
        } else if (node instanceof ArrayAccessNode n) {
            compile(n, out);
        } else if (node instanceof ArrayAssignNode n) {
            compile(n, out);
        } else {
            throw new IllegalStateException("Unhandled node " + node);
        }
    }

    private void emit(final int op, final DataOutputStream out) {
        try {
            final var ins = Code.make(op);
            out.write(ins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void emit(final int op, final int immediate, final DataOutputStream out) {
        try {
            final var ins = Code.make(op, immediate);
            out.write(ins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToOut(final DataOutputStream out, final byte[] bytes) {
        try {
            out.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
