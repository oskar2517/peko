package me.oskar.std.function;

import me.oskar.object.LObject;
import me.oskar.object.StringObject;

import java.util.ArrayList;

public class StringFunction implements Function {

    @Override
    public LObject invoke(ArrayList<LObject> arguments) {
        return new StringObject(arguments.get(0).toString());
    }

    @Override
    public String getName() {
        return "str";
    }

    @Override
    public int getParametersCount() {
        return 1;
    }
}
