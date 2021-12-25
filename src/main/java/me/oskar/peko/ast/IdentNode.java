package me.oskar.peko.ast;

import me.oskar.peko.ast.visitor.Visitor;

public class IdentNode extends Node {

    private final String value;

    public IdentNode(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(IDENT %s)", value);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
