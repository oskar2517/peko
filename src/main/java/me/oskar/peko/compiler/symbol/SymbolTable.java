package me.oskar.peko.compiler.symbol;

import java.util.HashMap;

public class SymbolTable {

    private int symbolIndex = 0;
    private int builtInSymbolIndex = 0;
    private int parameterSymbolIndex = -2;
    private final SymbolTable parentTable;
    private final HashMap<String, Symbol> symbols = new HashMap<>();

    public SymbolTable(final SymbolTable parentTable, final int initialSymbolIndex) {
        this.parentTable = parentTable;
        this.symbolIndex = initialSymbolIndex;
    }

    public SymbolTable(final SymbolTable parentTable) {
        this.parentTable = parentTable;
    }

    public SymbolTable() {
        this.parentTable = null;
    }

    public void enter(final String name, final SymbolEntry symbolEntry) {
        final Symbol symbol = new Symbol(symbolIndex, parentTable == null, symbolEntry);
        symbolIndex++;
        symbols.put(name, symbol);
    }

    public void enterBuiltIn(final String name, final BuiltInFunctionEntry builtInFunctionEntry) {
        final Symbol symbol = new Symbol(builtInSymbolIndex, true, builtInFunctionEntry);
        builtInSymbolIndex++;
        symbols.put(name, symbol);
    }

    public void enterParameter(final String name) {
        final Symbol symbol = new Symbol(parameterSymbolIndex, false, new VariableEntry());
        parameterSymbolIndex--;
        symbols.put(name, symbol);
    }

    public Symbol lookup(final String name) {
        if (symbols.containsKey(name)) {
            return symbols.get(name);
        }

        if (parentTable != null) {
            return parentTable.lookup(name);
        }

        return null;
    }

    public Symbol lookupOnThisTable(final String name) {
        return symbols.get(name);
    }

    public int getSymbolCount() {
        return symbolIndex;
    }

    public void increaseSymbolIndex(final int n) {
        symbolIndex += n;
    }
}
