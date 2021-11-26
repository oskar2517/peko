package me.oskar.std.function;

import me.oskar.object.LObject;

import java.util.ArrayList;

public interface Function {

    LObject invoke(final ArrayList<LObject> arguments);
    String getName();
    int getParametersCount();
}
