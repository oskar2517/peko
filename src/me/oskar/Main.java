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
                func modInv(a, b) {
                    var i = 0;
                    while i < b {
                        if (a * i) % b == 1 {
                            return i;
                        }
                        
                        i = i + 1;
                    }
                    
                    return 0;
                }
                
                func main() {
                    puts modInv(42, 2017);
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
