package me.oskar.peko.ast;

import me.oskar.peko.compiler.symbol.SymbolTable;
import me.oskar.peko.ast.visitor.Visitor;

import java.util.ArrayList;

public class BlockNode extends Node {

    private final ArrayList<Node> body = new ArrayList<>();
    private SymbolTable symbolTable;

    public ArrayList<Node> getBody() {
        return body;
    }

    public void addNode(final Node node) {
        body.add(node);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(final SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
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

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
