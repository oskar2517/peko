package me.oskar.std.function;

import me.oskar.object.LObject;
import me.oskar.object.NilObject;
import me.oskar.object.NumberObject;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ReadNumberFunction implements Function {

    @Override
    public LObject invoke(ArrayList<LObject> arguments) {
        try {
            var scanner = new Scanner(System.in);

            return new NumberObject(scanner.nextDouble());
        } catch (InputMismatchException e) {
            return new NilObject();
        }
    }

    @Override
    public String getName() {
        return "readNumber";
    }

    @Override
    public int getParametersCount() {
        return 0;
    }
}
