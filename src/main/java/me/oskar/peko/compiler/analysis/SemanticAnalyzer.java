package me.oskar.peko.compiler.analysis;

import me.oskar.peko.ast.*;
import me.oskar.peko.compiler.symbol.FunctionEntry;
import me.oskar.peko.compiler.symbol.SymbolTable;
import me.oskar.peko.compiler.symbol.UserFunctionEntry;
import me.oskar.peko.compiler.symbol.VariableEntry;
import me.oskar.peko.error.Error;
import me.oskar.peko.ast.visitor.BaseVisitor;

import java.util.Collections;

public class SemanticAnalyzer extends BaseVisitor {

    private final SymbolTable currentSymbolTable;

    public SemanticAnalyzer(final SymbolTable currentSymbolTable) {
        this.currentSymbolTable = currentSymbolTable;
    }

    @Override
    public void visit(final FileNode fileNode) {
        for (final var variable : fileNode.getVariables()) {
            variable.accept(this);
        }

        for (final var function : fileNode.getFunctions()) {
            function.accept(this);
        }

        if (fileNode.getMainFunction() != null) {
            fileNode.getMainFunction().accept(this);
        } else {
            Error.error("Main function missing.");
        }
    }

    @Override
    public void visit(final VariableDeclarationNode variableDeclarationNode) {
        if (currentSymbolTable.lookupOnThisTable(variableDeclarationNode.getName()) != null) {
            Error.error("Symbol `%s` already defined on this scope.", variableDeclarationNode.getName());
        }
        variableDeclarationNode.getValue().accept(this);
        currentSymbolTable.enter(variableDeclarationNode.getName(), new VariableEntry());
    }

    @Override
    public void visit(final ArrayAccessNode arrayAccessNode) {
        arrayAccessNode.getTarget().accept(this);
        arrayAccessNode.getIndex().accept(this);
    }

    @Override
    public void visit(final ArrayAssignNode arrayAssignNode) {
        arrayAssignNode.getTarget().accept(this);
        arrayAssignNode.getValue().accept(this);
    }

    @Override
    public void visit(final ArrayNode arrayNode) {
        for (final var node : arrayNode.getValue()) {
            node.accept(this);
        }
    }

    @Override
    public void visit(final BinaryOperatorNode binaryOperatorNode) {
        binaryOperatorNode.getLeft().accept(this);
        binaryOperatorNode.getRight().accept(this);
    }

    @Override
    public void visit(final BlockNode blockNode) {
        final var blockSymbolTable = new SymbolTable(currentSymbolTable, currentSymbolTable.getSymbolCount());
        final var blockSemanticAnalyzer = new SemanticAnalyzer(blockSymbolTable);

        for (final var s : blockNode.getBody()) {
            s.accept(blockSemanticAnalyzer);
        }

        currentSymbolTable.increaseSymbolIndex(blockSymbolTable.getSymbolCount());
        blockNode.setSymbolTable(blockSymbolTable);
    }

    @Override
    public void visit(final CallNode callNode) {
        final var symbol = currentSymbolTable.lookup(callNode.getFunctionName());
        if (symbol == null) {
            Error.error("Symbol `%s` undefined.", callNode.getFunctionName());
            return;
        }

        if (symbol.getSymbolEntry() instanceof FunctionEntry functionEntry) {
            if (functionEntry.getParametersCount() != callNode.getArguments().size()) {
                Error.error("Wrong number of arguments to function `%s`. Expected %s, got %s.",
                        callNode.getFunctionName(), functionEntry.getParametersCount(), callNode.getArguments().size());
            }
        } else {
            Error.error("Symbol `%s` is not a function.", callNode.getFunctionName());
        }

        for (final var a : callNode.getArguments()) {
            a.accept(this);
        }
    }

    @Override
    public void visit(final ExpressionStatementNode expressionStatementNode) {
        expressionStatementNode.getValue().accept(this);
    }

    @Override
    public void visit(final IfNode ifNode) {
        ifNode.getConsequence().accept(this);

        if (ifNode.getAlternative() != null) {
            ifNode.getAlternative().accept(this);
        }
    }

    @Override
    public void visit(final WhileNode whileNode) {
        whileNode.getBody().accept(this);
    }

    @Override
    public void visit(final ParameterDeclarationNode parameterDeclarationNode) {
        if (currentSymbolTable.lookupOnThisTable(parameterDeclarationNode.getName()) != null) {
            Error.error("Symbol `%s` already defined on this scope.", parameterDeclarationNode.getName());
        }
        currentSymbolTable.enterParameter(parameterDeclarationNode.getName());
    }

    @Override
    public void visit(final ReturnNode returnNode) {
        if (returnNode.getValue() != null) {
            returnNode.getValue().accept(this);
        }
    }

    @Override
    public void visit(final UnaryOperatorNode unaryOperatorNode) {
        unaryOperatorNode.getRight().accept(this);
    }

    @Override
    public void visit(final VariableAssignNode variableAssignNode) {
        final var symbol = currentSymbolTable.lookup(variableAssignNode.getName());

        if (symbol == null) {
            Error.error("Symbol `%s` undefined.", variableAssignNode.getName());
            return;
        }

        if (!(symbol.getSymbolEntry() instanceof VariableEntry)) {
            Error.error("Illegal use of function `%s`.", variableAssignNode.getName());
        }

        variableAssignNode.getValue().accept(this);
    }

    @Override
    public void visit(final FunctionNode functionNode) {
        if (currentSymbolTable.lookupOnThisTable(functionNode.getName()) != null) {
            Error.error("Symbol `%s` already defined on this scope.", functionNode.getName());
        }
        final var localSymbolTable = new SymbolTable(currentSymbolTable);
        final var localSemanticAnalyzer = new SemanticAnalyzer(localSymbolTable);

        Collections.reverse(functionNode.getParameters());
        for (final var p : functionNode.getParameters()) {
            p.accept(localSemanticAnalyzer);
        }
        final var functionEntry = new UserFunctionEntry(functionNode.getParameters().size(), localSymbolTable);
        currentSymbolTable.enter(functionNode.getName(), functionEntry);

        functionNode.getBody().accept(localSemanticAnalyzer);
    }

    @Override
    public void visit(final IdentNode identNode) {
        final var symbol = currentSymbolTable.lookup(identNode.getValue());

        if (symbol == null) {
            Error.error("Symbol `%s` undefined.", identNode.getValue());
        }
    }
}
