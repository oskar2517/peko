package me.oskar.peko.std.function;

import me.oskar.peko.object.LObject;

import java.util.ArrayList;

public interface Function {

    LObject invoke(final ArrayList<LObject> arguments);
    String getName();
    int getParametersCount();
}
