package me.oskar.peko.ast;

import me.oskar.peko.ast.visitor.Visitor;

public class NilNode extends Node {

    @Override
    public String toString() {
        return "(NIL)";
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
