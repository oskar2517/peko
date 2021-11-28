package me.oskar.peko.ast;

import java.util.ArrayList;

public class BlockNode extends Node {

    private final ArrayList<Node> body = new ArrayList<>();

    public ArrayList<Node> getBody() {
        return body;
    }

    public void addNode(final Node node) {
        body.add(node);
    }

    @Override
    public String toString() {
        final var s = new StringBuilder();
        s.append("(BLOCK");
        for (var node : body) {
            s.append(" ");
            s.append(node.toString());
        }

        return s.toString();
    }
}
