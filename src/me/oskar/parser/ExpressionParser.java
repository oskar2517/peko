package me.oskar.parser;

import me.oskar.ast.*;
import me.oskar.error.Error;
import me.oskar.lexer.Lexer;
import me.oskar.lexer.TokenType;

/**
 * Parses expressions into a subtree.
 *
 * Examples:
 * 2 * (3 + 5)
 * 5 + x
 * 3 == 2
 */
public class ExpressionParser {

    private final Parser parser;
    private final Lexer lexer;

    public ExpressionParser(final Parser parser, final Lexer lexer) {
        this.parser = parser;
        this.lexer = lexer;
    }

    public Node parseExpression() {
        return parseDisjunction();
    }

    /**
     * Parses expressions of the following types:
     * - left || right
     *
     * @return The parsed node.
     */
    private Node parseDisjunction() {
        var left = parseConjunction();

        while (parser.getCurrentToken().getType() == TokenType.OR) {
            parser.nextToken();
            final var right = parseConjunction();

            left = new BinaryOperatorNode(OperatorType.OR, left, right);
        }

        return left;
    }

    /**
     * Parses expressions of the following types:
     * - left && right
     *
     * @return The parsed node.
     */
    private Node parseConjunction() {
        var left = parseComparison();

        while (parser.getCurrentToken().getType() == TokenType.AND) {
            parser.nextToken();
            final var right = parseComparison();

            left = new BinaryOperatorNode(OperatorType.AND, left, right);
        }

        return left;
    }

    /**
     * Parses expressions of the following types:
     * - left < right
     * - left > right
     * - left == right
     *
     * @return The parsed node.
     */
    private Node parseComparison() {
        var left = parseNumeric();

        while (true) {
            OperatorType type;
            switch (parser.getCurrentToken().getType()) {
                case LESS_THAN: type = OperatorType.LT; break;
                case LESS_THAN_OR_EQUAL: type = OperatorType.LE; break;
                case GREATER_THAN: type = OperatorType.GT; break;
                case GREATER_THAN_OR_EQUAL: type = OperatorType.GE; break;
                case EQUALS: type = OperatorType.EQ; break;
                case NOT_EQUALS: type = OperatorType.NE; break;
                default: return left;
            }

            parser.nextToken();
            final var right = parseNumeric();
            left = new BinaryOperatorNode(type, left, right);
        }
    }

    /**
     * Parses expressions of the following types:
     * - left + right
     * - left - right
     *
     * @return The parsed node.
     */
    private Node parseNumeric() {
        var left = parseTerm();

        while (true) {
            OperatorType type;
            switch (parser.getCurrentToken().getType()) {
                case PLUS: type = OperatorType.ADD; break;
                case MINUS: type = OperatorType.SUB; break;
                default: return left;
            }

            parser.nextToken();
            final var right = parseTerm();
            left = new BinaryOperatorNode(type, left, right);
        }
    }

    /**
     * Parses expressions of the following types:
     * - left * right
     * - left / right
     * - left % right
     *
     * @return The parsed node.
     */
    private Node parseTerm() {
        var left = parseSignedFactor();

        while (true) {
            OperatorType type;
            switch (parser.getCurrentToken().getType()) {
                case ASTERISK: type = OperatorType.MUL; break;
                case SLASH: type = OperatorType.DIV; break;
                case PERCENT: type = OperatorType.MOD; break;
                default: return left;
            }

            parser.nextToken();
            final var right = parseSignedFactor();
            left = new BinaryOperatorNode(type, left, right);
        }
    }

    /**
     * Parses unary expressions of the following types:
     * - -right
     * - !right
     * @return The parsed node.
     */
    private Node parseSignedFactor() {
        switch (parser.getCurrentToken().getType()) {
            case MINUS -> {
                parser.nextToken();

                final var right = parseFactor();
                return new UnaryOperatorNode(OperatorType.NEG, right);
            }
            case BANG -> {
                parser.nextToken();

                final var right = parseFactor();
                return new UnaryOperatorNode(OperatorType.NOT, right);
            }
            default -> {
                return parseFactor();
            }
        }
    }

    /**
     * Parses a factor into a Node.
     * A factor is the smallest possible expression with an inherent value.
     *
     * Examples:
     * 2
     * "string"
     * x
     *
     * @return The factor.
     */
    private Node parseFactor() {
        switch (parser.getCurrentToken().getType()) {
            case LPAREN -> {
                parser.nextToken();
                final var comp = parseComparison();
                parser.nextToken();

                return comp;
            }
            case IDENT -> {
                if (lexer.peekToken().getType() == TokenType.LPAREN) {
                    return parser.parseCall();
                } else {
                    final var ident = new IdentNode(parser.getCurrentToken().getLiteral());
                    parser.nextToken();

                    return ident;
                }
            }
            case STRING -> {
                final var string = new StringNode(parser.getCurrentToken().getLiteral());
                parser.nextToken();

                return string;
            }
            case NUMBER -> {
                final var number = new NumberNode(Double.parseDouble(parser.getCurrentToken().getLiteral()));
                parser.nextToken();

                return number;
            }
            case TRUE -> {
                final var bool = new BooleanNode(true);
                parser.nextToken();

                return bool;
            }
            case FALSE -> {
                final var bool = new BooleanNode(false);
                parser.nextToken();

                return bool;
            }
            case NIL -> {
                parser.nextToken();

                return new NilNode();
            }
            default -> {
                Error.error("Unexpected token `%s`.", parser.getCurrentToken().getLiteral());
                return null;
            }
        }
    }
}
