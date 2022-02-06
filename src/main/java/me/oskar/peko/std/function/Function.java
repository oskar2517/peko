package me.oskar.peko.std.function;

import me.oskar.peko.object.PekoObject;

import java.util.ArrayList;

public interface Function {

    PekoObject invoke(final ArrayList<PekoObject> arguments);
    String getName();
    int getParametersCount();
}
