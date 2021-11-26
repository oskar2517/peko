package me.oskar.std.function;

import me.oskar.object.ArrayObject;
import me.oskar.object.LObject;
import me.oskar.object.NumberObject;
import me.oskar.object.StringObject;

import java.util.ArrayList;

public class LengthFunction implements Function {

    @Override
    public LObject invoke(ArrayList<LObject> arguments) {
        if (arguments.get(0) instanceof ArrayObject o) {
            return new NumberObject(o.getValue().size());
        } else if (arguments.get(0) instanceof StringObject o) {
            return new NumberObject(o.getValue().length());
        } else {
            return new NumberObject(0);
        }
    }

    @Override
    public String getName() {
        return "len";
    }

    @Override
    public int getParametersCount() {
        return 1;
    }
}
