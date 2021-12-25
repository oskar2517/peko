package me.oskar.peko.compiler.symbol;

public class FunctionEntry extends SymbolEntry {

    private final int parametersCount;

    public FunctionEntry(final int parametersCount) {
        this.parametersCount = parametersCount;
    }

    public int getParametersCount() {
        return parametersCount;
    }
}
