package me.oskar.peko.ast;

import me.oskar.peko.ast.visitor.Visitor;

public class BooleanNode extends Node {

    private final boolean value;

    public BooleanNode(final boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(BOOL %s)", value);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
