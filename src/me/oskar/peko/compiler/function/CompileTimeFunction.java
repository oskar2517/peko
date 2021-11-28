package me.oskar.peko.compiler.function;

import me.oskar.peko.compiler.symbol.FunctionScope;

public class CompileTimeFunction {

    private final int position;
    private final int parametersCount;
    private final FunctionScope scope;

    public CompileTimeFunction(final int position, final int parametersCount, final FunctionScope scope) {
        this.position = position;
        this.parametersCount = parametersCount;
        this.scope = scope;
    }

    public int getPosition() {
        return position;
    }

    public FunctionScope getScope() {
        return scope;
    }

    public int getParametersCount() {
        return parametersCount;
    }
}
