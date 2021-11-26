package main.me.oskar;

import main.me.oskar.compiler.Compiler;
import main.me.oskar.lexer.Lexer;
import main.me.oskar.parser.Parser;
import main.me.oskar.vm.VirtualMachine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: peko <input>");
            System.exit(1);
        }

        final var code = new String(Files.readAllBytes(Paths.get(args[0])));

        final var lexer = new Lexer(code);
        final var parser = new Parser(lexer);
        parser.generateAst();

        final var compiler = new Compiler(parser.getAst());
        final var bytecode = compiler.compile();

        final var vm = new VirtualMachine(bytecode);
        vm.execute();
    }
}
