package me.oskar.peko.std.function;

import me.oskar.peko.object.PekoObject;
import me.oskar.peko.object.NilObject;

import java.util.ArrayList;

public class PrintFunction implements Function {

    @Override
    public PekoObject invoke(ArrayList<PekoObject> arguments) {
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
