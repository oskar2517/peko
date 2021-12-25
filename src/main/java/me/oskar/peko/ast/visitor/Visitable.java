package me.oskar.peko.ast.visitor;

public interface Visitable {

    void accept(final Visitor visitor);
}
