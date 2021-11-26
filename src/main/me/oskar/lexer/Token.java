package main.me.oskar.lexer;

public class Token {

    private final TokenType type;
    private final String literal;

    public Token(final TokenType type, final String literal) {
        this.type = type;
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "(" + type + ", " + literal + ")";
    }
}
