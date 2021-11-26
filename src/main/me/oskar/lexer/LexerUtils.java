package main.me.oskar.lexer;

public class LexerUtils {

    /**
     * Returns whether the given char is alphanumeric.
     * @param c The char.
     * @return Whether char is alphanumeric.
     */
    static boolean isAlphaNumeric(final char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }

    /**
     * Returns whether the given char represents a number.
     * @param c The char.
     * @return Whether char is a number.
     */
    static boolean isNumber(final char c) {
        return Character.isDigit(c);
    }

    /**
     * Returns whether the given char is a whitespace.
     * @param c The char.
     * @return Whether char is a whitespace.
     */
    static boolean isWhitespace(final char c) {
        return Character.isWhitespace(c);
    }
}
