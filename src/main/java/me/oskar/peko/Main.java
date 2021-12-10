package me.oskar.peko;

import me.oskar.peko.compiler.Compiler;
import me.oskar.peko.lexer.Lexer;
import me.oskar.peko.parser.Parser;
import me.oskar.peko.vm.VirtualMachine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: peko <input> [--dump]");
            System.exit(1);
        }

        final var file = args[0];

        if (file.endsWith(".peko")) {
            final var code = new String(Files.readAllBytes(Paths.get(file)));

            final var lexer = new Lexer(code);
            final var parser = new Parser(lexer);
            parser.generateAst();

            final var compiler = new Compiler(parser.getAst());
            final var bytecode = compiler.compile();

            if (Arrays.asList(args).contains("--dump")) {
                Files.write(Path.of("file.bin"), bytecode);
                System.exit(0);
            }

            final var vm = new VirtualMachine(bytecode);
            vm.execute();
        } else {
            final var bytecode = Files.readAllBytes(Paths.get(file));

            final var vm = new VirtualMachine(bytecode);
            vm.execute();
        }
    }
}
