package me.oskar.peko.std.function;

import me.oskar.peko.object.PekoObject;
import me.oskar.peko.object.StringObject;

import java.util.ArrayList;

public class StringFunction implements Function {

    @Override
    public PekoObject invoke(ArrayList<PekoObject> arguments) {
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
