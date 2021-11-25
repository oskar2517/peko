package me.oskar;

import me.oskar.compiler.Compiler;
import me.oskar.lexer.Lexer;
import me.oskar.parser.Parser;
import me.oskar.vm.VirtualMachine;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        final var code =
                """
                func sub(x, y) {
                    return x - y;
                }
                            
                func add(x, y) {
                    x = 2;
                    return x;
                }
                """;

        final var lexer = new Lexer(code);
        final var parser = new Parser(lexer);
        parser.generateAst();

        System.out.println(parser.getAst());

        final var compiler = new Compiler();
        final var bytecode = compiler.compile(parser.getAst());

        final var vm = new VirtualMachine(bytecode);
        vm.execute();
    }
}
