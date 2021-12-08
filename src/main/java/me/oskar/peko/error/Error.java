package me.oskar.peko.error;

public class Error {

    public static void error(final String message, final Object... args) {
        final var m = String.format(message, args);
        System.out.println("Error: " + m);
        System.exit(1);
    }
}
