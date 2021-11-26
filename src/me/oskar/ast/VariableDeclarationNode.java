package me.oskar.ast;

public class VariableDeclarationNode extends Node {

    private final String name;
    private final Node value;

    public VariableDeclarationNode(final String name, final Node value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Node getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(VAR_DECL %s %S)", name, value);
    }
}
