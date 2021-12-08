package me.oskar.peko.compiler.symbol;

public class Symbol {

    private final int index;
    private final boolean global;
    private boolean initialized = false;

    public Symbol(final int index, final boolean global) {
        this.index = index;
        this.global = global;
    }

    public Symbol(final int index, final boolean global, final boolean initialized) {
        this.index = index;
        this.global = global;
        this.initialized = initialized;
    }

    public int getIndex() {
        return index;
    }

    public boolean isGlobal() {
        return global;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void initialize() {
        initialized = true;
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", global, index);
    }
}
