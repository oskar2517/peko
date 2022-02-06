package me.oskar.peko.std.function;

import me.oskar.peko.object.ArrayObject;
import me.oskar.peko.object.PekoObject;
import me.oskar.peko.object.NumberObject;
import me.oskar.peko.object.StringObject;

import java.util.ArrayList;

public class LengthFunction implements Function {

    @Override
    public PekoObject invoke(ArrayList<PekoObject> arguments) {
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
