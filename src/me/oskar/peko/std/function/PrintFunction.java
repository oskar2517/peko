package me.oskar.peko.std.function;

import me.oskar.peko.object.LObject;
import me.oskar.peko.object.NilObject;

import java.util.ArrayList;

public class PrintFunction implements Function {

    @Override
    public LObject invoke(ArrayList<LObject> arguments) {
        System.out.print(arguments.get(0));

        return new NilObject();
    }

    @Override
    public String getName() {
        return "print";
    }

    @Override
    public int getParametersCount() {
        return 1;
    }
}
