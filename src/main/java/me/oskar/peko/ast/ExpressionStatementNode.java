package me.oskar.peko.ast;

import me.oskar.peko.ast.visitor.Visitor;

public class ExpressionStatementNode extends Node {

    private final Node value;

    public ExpressionStatementNode(final Node value) {
        this.value = value;
    }

    public Node getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(EXPR_STMT %s)", value);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
