package me.oskar.peko.compiler.symbol;

public class UserFunctionEntry extends FunctionEntry {

    private final SymbolTable symbolTable;
    private int position;

    public UserFunctionEntry(final int parametersCount, final SymbolTable symbolTable) {
        super(parametersCount);

        this.symbolTable = symbolTable;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(final int position) {
        this.position = position;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
