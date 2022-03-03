package me.oskar.peko.lexer;

/**
 * Turns a source code string into a stream of tokens.
 *
 * Example:
 * x = 2;
 * if x == 2 {
 *     puts "ok";
 * }
 *
 * Tokens:
 * Ident(x)
 * Assign
 * Number(2)
 * Semicolon
 * If
 * Ident(x)
 * Equals
 * Number(2)
 * LBrace
 * Puts
 * String(ok)
 * Semiclon
 * RBrace
 */
public class Lexer {

    private static final char EOF = '\0';

    private final String code;
    private char currentChar = EOF;
    private int position = 0;

    public Lexer(final String code) {
        this.code = code;
    }

    /**
     * Reads the next char from the input string and stores it in [currentChar].
     */
    private void readChar() {
        if (position >= code.length()) {
            currentChar = EOF;
        } else {
            currentChar = code.charAt(position);
        }

        position++;
    }

    /**
     * Looks one char ahead. Does NOT update [currentChar].
     * @return The next char.
     */
    private char peekChar() {
        if (position >= code.length()) {
            return EOF;
        } else {
            return code.charAt(position);
        }
    }

    /**
     * Reads an alphanumeric identifier from the input string.
     * @return The identifier.
     */
    private String readIdent() {
        final int startPosition = position;

        while (LexerUtils.isAlphaNumeric(peekChar())) {
            readChar();
        }

        return code.substring(startPosition - 1, position);
    }

    /**
     * Reads a string literal from the input string and returns it without quotation marks.
     * @return The string.
     */
    private String readString() {
        readChar();
        final int startPosition = position;

        if (currentChar != '\"') {
            while (peekChar() != '\"' && peekChar() != EOF) {
                readChar();
            }

            readChar();
        }

        return code.substring(startPosition - 1, position - 1);
    }

    /**
     * Reads a decimal number from the input string and returns it as a string.
     * @return The number.
     */
    private String readNumber() {
        final int startPosition = position;

        while (LexerUtils.isNumber(peekChar())) {
            readChar();
        }

        if (peekChar() == '.') {
            readChar();

            while (LexerUtils.isNumber(peekChar())) {
                readChar();
            }
        }

        return code.substring(startPosition - 1, position);
    }

    /**
     * Skips all types of whitespcaes.
     * These include
     * - spaces
     * - \n
     * - \r
     * - \t
     */
    private void eatWhitespace() {
        while (LexerUtils.isWhitespace(currentChar)) {
            readChar();
        }
    }

    /**
     * Tokenizes the entire input string and prints all tokens to stdout.
     */
    public void printTokens() {
        while (true) {
            final var token = readToken();
            System.out.println(token);
            if (token.getType() == TokenType.EOF) {
                break;
            }
        }
    }

    /**
     * Reads a token and returns it.
     * @return The token.
     */
    public Token readToken() {
        readChar();
        eatWhitespace();

        switch (currentChar) {
            case ';': return new Token(TokenType.SEMICOLON, ";");
            case ',': return new Token(TokenType.COMMA, ",");
            case '(': return new Token(TokenType.LPAREN, "(");
            case ')': return new Token(TokenType.RPAREN, ")");
            case '{': return new Token(TokenType.LBRACE, "{");
            case '}': return new Token(TokenType.RBRACE, "}");
            case '+': return new Token(TokenType.PLUS, "+");
            case '-': return new Token(TokenType.MINUS, "-");
            case '*': return new Token(TokenType.ASTERISK, "*");
            case '/': return new Token(TokenType.SLASH, "/");
            case '%': return new Token(TokenType.PERCENT, "%");
            case '[': return new Token(TokenType.LBRACK, "[");
            case ']': return new Token(TokenType.RBRACK, "]");
            case '&': {
                if (peekChar() == '&') {
                    readChar();
                    return new Token(TokenType.AND, "&&");
                } else {
                    return new Token(TokenType.ILLEGAL, "&");
                }
            }
            case '|': {
                if (peekChar() == '|') {
                    readChar();
                    return new Token(TokenType.OR, "||");
                } else {
                    return new Token(TokenType.ILLEGAL, "|");
                }
            }
            case '!': {
                if (peekChar() == '=') {
                    readChar();
                    return new Token(TokenType.NOT_EQUALS, "!=");
                } else {
                    return new Token(TokenType.BANG, "!");
                }
            }
            case '<': {
                if (peekChar() == '=') {
                    readChar();
                    return new Token(TokenType.LESS_THAN_OR_EQUAL, "<=");
                } else {
                    return new Token(TokenType.LESS_THAN, "<");
                }
            }
            case '>': {
                if (peekChar() == '=') {
                    readChar();
                    return new Token(TokenType.GREATER_THAN_OR_EQUAL, ">=");
                } else {
                    return new Token(TokenType.GREATER_THAN, ">");
                }
            }
            case '\"': return new Token(TokenType.STRING, readString());
            case '=': {
                if (peekChar() == '=') {
                    readChar();
                    return new Token(TokenType.EQUALS, "==");
                } else {
                    return new Token(TokenType.ASSIGN, "=");
                }
            }
            default: {
                if (position > code.length()) {
                    return new Token(TokenType.EOF, String.valueOf(EOF));
                }
                if (LexerUtils.isNumber(currentChar)) {
                    return new Token(TokenType.NUMBER, readNumber()); // 2.34 2345
                }
                if (LexerUtils.isAlphaNumeric(currentChar)) {
                    final var ident = readIdent();

                    if (Keyword.isKeyword(ident)) {
                        return new Token(Keyword.resolve(ident), ident);
                    } else {
                        return new Token(TokenType.IDENT, ident);
                    }
                }

                return new Token(TokenType.ILLEGAL, String.valueOf(currentChar));
            }
        }
    }

    /**
     * Looks one token ahead and returns it. Does NOT modify this instance.
     * @return The token.
     */
    public Token peekToken() {
        final var oPosition = position;
        final var oCurrentChar = currentChar;
        final var token = readToken();
        position = oPosition;
        currentChar = oCurrentChar;

        return token;
    }
}
