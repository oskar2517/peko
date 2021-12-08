package me.oskar.peko.ast;

import java.util.ArrayList;

public class ArrayNode extends Node {

    private final ArrayList<Node> value;

    public ArrayNode(final ArrayList<Node> value) {
        this.value = value;
    }

    public ArrayList<Node> getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(ARRAY %s)", value);
    }
}
