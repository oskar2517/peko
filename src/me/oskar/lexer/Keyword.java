package me.oskar.lexer;

import java.util.Map;

public class Keyword {

    /**
     * Map of all reserved keywords.
     */
    static final Map<String, TokenType> keywords = Map.ofEntries(
            Map.entry("if", TokenType.IF),
            Map.entry("else", TokenType.ELSE),
            Map.entry("while", TokenType.WHILE),
            Map.entry("true", TokenType.TRUE),
            Map.entry("false", TokenType.FALSE),
            Map.entry("puts", TokenType.PUTS),
            Map.entry("func", TokenType.FUNC),
            Map.entry("var", TokenType.VAR),
            Map.entry("return", TokenType.RETURN),
            Map.entry("nil", TokenType.NIL)
    );

    /**
     * Returns whether the given literal corresponds to a reserved keyword.
     * @param literal The read literal.
     * @return Whether literal is a keyword.
     */
    public static boolean isKeyword(final String literal) {
        return keywords.containsKey(literal);
    }

    /**
     * Returns the corresponding token type for a literal.
     * @param literal The read literal.
     * @return Token type of the keyword.
     */
    public static TokenType resolve(final String literal) {
        return keywords.get(literal);
    }
}
