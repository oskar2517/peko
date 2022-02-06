package me.oskar.peko.std.function;

import me.oskar.peko.object.PekoObject;
import me.oskar.peko.object.NilObject;

import java.util.ArrayList;

public class PrintlnFunction implements Function {

    @Override
    public PekoObject invoke(final ArrayList<PekoObject> arguments) {
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
