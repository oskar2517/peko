package me.oskar.peko.ast;

import me.oskar.peko.ast.visitor.Visitor;

public class ReturnNode extends Node {

    private final Node value;

    public ReturnNode(final Node value) {
        this.value = value;
    }

    public Node getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("RET %s", value);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
