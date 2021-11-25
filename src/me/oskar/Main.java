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
                func test() {
                    var x = 2;
                    var y = 2;
                    puts x + y;
                }
                
                func main() {
                    var x = 2;
                    if true {
                        var x = 3;
                        puts x;
                    }
                    puts x;
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
