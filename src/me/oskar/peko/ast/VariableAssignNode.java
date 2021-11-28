package me.oskar.peko.ast;

public class VariableAssignNode extends Node {

    private final String name;
    private final Node value;

    public VariableAssignNode(final String name, final Node value) {
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
        return String.format("(VAR_ASGN %s %s)", name, value);
    }
}
