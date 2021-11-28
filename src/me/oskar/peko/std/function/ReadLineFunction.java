package me.oskar.peko.std.function;

import me.oskar.peko.object.LObject;
import me.oskar.peko.object.NilObject;
import me.oskar.peko.object.StringObject;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ReadLineFunction implements Function {

    @Override
    public LObject invoke(ArrayList<LObject> arguments) {
        try {
            var scanner = new Scanner(System.in);

            return new StringObject(scanner.nextLine());
        } catch (InputMismatchException e) {
            return new NilObject();
        }
    }

    @Override
    public String getName() {
        return "readLine";
    }

    @Override
    public int getParametersCount() {
        return 0;
    }
}
