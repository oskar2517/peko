package me.oskar.peko.compiler.symbol;

public record Symbol(int index, boolean global, SymbolEntry symbolEntry) {

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

    public SymbolEntry getSymbolEntry() {
        return symbolEntry;
    }
}
