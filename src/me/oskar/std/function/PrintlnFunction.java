package me.oskar.std.function;

import me.oskar.object.LObject;
import me.oskar.object.NilObject;

import java.util.ArrayList;

public class PrintlnFunction implements Function {

    @Override
    public LObject invoke(final ArrayList<LObject> arguments) {
        System.out.println(arguments.get(0));

        return new NilObject();
    }

    @Override
    public String getName() {
        return "println";
    }

    @Override
    public int getParametersCount() {
        return 1;
    }
}
