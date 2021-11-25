package me.oskar.compiler.symbol;

public class Symbol {

    private final int index;
    private final boolean global;

    public Symbol(final int index, final boolean global) {
        this.index = index;
        this.global = global;
    }

    public int getIndex() {
        return index;
    }

    public boolean isGlobal() {
        return global;
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", global, index);
    }
}
