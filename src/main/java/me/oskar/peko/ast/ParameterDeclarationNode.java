package me.oskar.peko.ast;

import me.oskar.peko.ast.visitor.Visitor;

public class ParameterDeclarationNode extends Node {

    private final String name;

    public ParameterDeclarationNode(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("(PARAM_DECL %s)", name);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
