package main.me.oskar.std;

import main.me.oskar.std.function.*;

import java.util.ArrayList;

public class BuiltInTable {

    private static final BuiltInTable instance = new BuiltInTable();
    private final ArrayList<Function> functions = new ArrayList<>();

    private BuiltInTable() {
        functions.add(new PrintlnFunction());
        functions.add(new PrintFunction());
        functions.add(new ReadNumberFunction());
        functions.add(new ReadLineFunction());
        functions.add(new LengthFunction());
        functions.add(new StringFunction());
    }

    public boolean isBuiltInFunction(final String name) {
        return getBuiltInFunctionIndex(name) != -1;
    }

    public int getBuiltInFunctionIndex(final String name) {
        for (var i = 0; i < functions.size(); i++) {
            if (functions.get(i).getName().equals(name)) {
                return i;
            }
        }

        return -1;
    }

    public Function getBuiltInFunction(final int index) {
        return functions.get(index);
    }

    public static BuiltInTable getInstance() {
        return instance;
    }
}
