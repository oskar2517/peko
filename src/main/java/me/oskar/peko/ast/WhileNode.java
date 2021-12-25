package me.oskar.peko.ast;

import me.oskar.peko.ast.visitor.Visitor;

public class WhileNode extends Node {

    private final Node condition;
    private final BlockNode body;

    public WhileNode(final Node condition, final BlockNode body) {
        this.condition = condition;
        this.body = body;
    }

    public Node getCondition() {
        return condition;
    }

    public BlockNode getBody() {
        return body;
    }

    @Override
    public String toString() {
        return String.format("(WHILE %s %S)", condition, body);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
