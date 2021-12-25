package me.oskar.peko.compiler.symbol;

public class Symbol {

    private final int index;
    private final boolean global;
    private final SymbolEntry symbolEntry;

    public Symbol(final int index, final boolean global, final SymbolEntry symbolEntry) {
        this.index = index;
        this.global = global;
        this.symbolEntry = symbolEntry;
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

    public SymbolEntry getSymbolEntry() {
        return symbolEntry;
    }
}
