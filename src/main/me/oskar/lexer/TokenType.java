package main.me.oskar.lexer;

public enum TokenType {
    NUMBER,
    STRING,
    TRUE,
    FALSE,

    IDENT,

    LPAREN,
    RPAREN,
    LBRACE,
    RBRACE,
    LBRACK,
    RBRACK,

    PLUS,
    MINUS,
    ASTERISK,
    SLASH,
    PERCENT,
    ASSIGN,
    EQUALS,
    NOT_EQUALS,
    LESS_THAN,
    LESS_THAN_OR_EQUAL,
    GREATER_THAN,
    GREATER_THAN_OR_EQUAL,
    BANG,
    OR,
    AND,

    IF,
    ELSE,
    WHILE,
    PUTS,
    FUNC,
    VAR,
    RETURN,
    NIL,

    SEMICOLON,
    COMMA,
    ILLEGAL,
    EOF
}
