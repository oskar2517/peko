package me.oskar.ast;

public class PutsNode extends Node {

    private final Node value;

    public PutsNode(final Node value) {
        this.value = value;
    }

    public Node getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "(PUTS" + " " + value + ")";
    }
}
