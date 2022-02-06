package me.oskar.peko.compiler;

import me.oskar.peko.ast.*;
import me.oskar.peko.compiler.analysis.NameAnalysisVisitor;
import me.oskar.peko.compiler.symbol.BuiltInFunctionEntry;
import me.oskar.peko.compiler.symbol.SymbolTable;
import me.oskar.peko.std.BuiltInTable;

import java.io.IOException;

public class Compiler {

    private final FileNode ast;
    private final SymbolTable globalSymbolTable = new SymbolTable();

    public Compiler(final FileNode ast) {
        this.ast = ast;
    }

    private void initGlobalTable() {
        for (final var function : BuiltInTable.getInstance().getFunctions()) {
            globalSymbolTable.enterBuiltIn(function.getName(), new BuiltInFunctionEntry(function.getParametersCount()));
        }
    }

    private void performNameAnalysis() {
        final var nameAnalysisVisitor = new NameAnalysisVisitor(globalSymbolTable);
        nameAnalysisVisitor.visit(ast);
    }

    public byte[] compile() throws IOException {
        initGlobalTable();
        performNameAnalysis();

        final var codeGenVisitor = new CodeGeneratorVisitor(globalSymbolTable);
        codeGenVisitor.visit(ast);

        return codeGenVisitor.getBytecode();
    }
}
