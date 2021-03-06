package me.oskar.peko.parser;

import me.oskar.peko.ast.*;
import me.oskar.peko.error.Error;
import me.oskar.peko.lexer.Lexer;
import me.oskar.peko.lexer.Token;
import me.oskar.peko.lexer.TokenType;

import java.util.ArrayList;

public class Parser {

    private final Lexer lexer;
    private Token currentToken;
    private final ExpressionParser expressionParser;
    private final FileNode ast = new FileNode();

    public Parser(final Lexer lexer) {
        this.lexer = lexer;

        expressionParser = new ExpressionParser(this, lexer);
        nextToken();
    }

    public void generateAst() {
        while (currentToken.getType() != TokenType.EOF) {
            parseGlobal(ast);
        }
    }

    /**
     * Parses the entire source string into an AST.
     * @return The AST.
     */
    public FileNode getAst() {
        return ast;
    }

    protected Token getCurrentToken() {
        return currentToken;
    }

    /**
     * Advances to the next token.
     */
    protected void nextToken() {
        currentToken = lexer.readToken();
    }

    /**
     * Checks whether the current token is of type [type]. If not, an error message will be printed
     * and the programs exits.
     * @param type The expected type.
     */
    protected void expectToken(final TokenType type) {
        if (currentToken.getType() != type) {
            Error.error("Unexpected token `%s`.", currentToken.getLiteral());
        }
    }

    /**
     * Parses an array node.
     * @return The node.
     */
    protected ArrayNode parseArray() {
        nextToken();

        final var value = new ArrayList<Node>();

        while (currentToken.getType() != TokenType.RBRACK) {
            value.add(expressionParser.parseExpression());
            if (currentToken.getType() == TokenType.COMMA && lexer.peekToken().getType() == TokenType.RBRACK) {
                expectToken(TokenType.RBRACK);
            } else if (currentToken.getType() == TokenType.COMMA) {
                nextToken();
            } else {
                expectToken(TokenType.RBRACK);
            }
        }

        nextToken();

        return new ArrayNode(value);
    }

    /**
     * Parses a variable declaration node.
     * @return The node.
     */
    protected VariableDeclarationNode parseVariableDeclaration() {
        nextToken();
        final var name = currentToken.getLiteral();
        nextToken();
        expectToken(TokenType.ASSIGN);
        nextToken();
        final var value = expressionParser.parseExpression();
        expectToken(TokenType.SEMICOLON);
        nextToken();

        return new VariableDeclarationNode(name, value);
    }

    /**
     * Parses a variable assign node.
     * @return The node.
     */
    protected VariableAssignNode parseVariableAssign() {
        final var name = currentToken.getLiteral();
        nextToken();
        expectToken(TokenType.ASSIGN);
        nextToken();
        final var value = expressionParser.parseExpression();
        expectToken(TokenType.SEMICOLON);
        nextToken();

        return new VariableAssignNode(name, value);
    }

    /**
     * Parses an if node.
     * @return The node.
     */
    protected IfNode parseIf() {
        nextToken();
        final var condition = expressionParser.parseExpression();
        expectToken(TokenType.LBRACE);
        nextToken();
        final var consequence = new BlockNode();
        while (currentToken.getType() != TokenType.RBRACE && currentToken.getType() != TokenType.EOF) {
            parseToken(consequence);
        }
        expectToken(TokenType.RBRACE);

        BlockNode alternative = null;
        if (lexer.peekToken().getType() == TokenType.ELSE) {
            nextToken();
            nextToken();
            expectToken(TokenType.LBRACE);
            nextToken();
            alternative = new BlockNode();
            while (currentToken.getType() != TokenType.RBRACE && currentToken.getType() != TokenType.EOF) {
                parseToken(alternative);
            }
            expectToken(TokenType.RBRACE);
        }

        nextToken();

        return new IfNode(condition, consequence, alternative);
    }

    /**
     * Parses a while node.
     * @return The node.
     */
    protected WhileNode parseWhile() {
        nextToken();
        final var condition = expressionParser.parseExpression();
        expectToken(TokenType.LBRACE);
        nextToken();
        final var body = new BlockNode();
        while (currentToken.getType() != TokenType.RBRACE && currentToken.getType() != TokenType.EOF) {
            parseToken(body);
        }
        expectToken(TokenType.RBRACE);
        nextToken();

        return new WhileNode(condition, body);
    }

    /**
     * Parses a function node.
     * @return The node.
     */
    protected FunctionNode parseFunction() {
        nextToken();
        expectToken(TokenType.IDENT);
        final var name = currentToken.getLiteral();
        nextToken();
        expectToken(TokenType.LPAREN);
        nextToken();

        final var parameters = new ArrayList<ParameterDeclarationNode>();
        while (currentToken.getType() != TokenType.RPAREN) {
            if (currentToken.getType() == TokenType.IDENT) {
                final var p = new ParameterDeclarationNode(currentToken.getLiteral());
                parameters.add(p);
                if (lexer.peekToken().getType() != TokenType.COMMA && lexer.peekToken().getType() != TokenType.RPAREN) {
                    expectToken(TokenType.COMMA);
                }
            } else if (currentToken.getType() == TokenType.COMMA && lexer.peekToken().getType() == TokenType.RPAREN) {
                expectToken(TokenType.IDENT);
            } else {
                expectToken(TokenType.COMMA);
            }

            nextToken();
        }

        nextToken();
        expectToken(TokenType.LBRACE);
        nextToken();
        final var body = new BlockNode();
        while (currentToken.getType() != TokenType.RBRACE && currentToken.getType() != TokenType.EOF) {
            parseToken(body);
        }
        expectToken(TokenType.RBRACE);
        nextToken();

        return new FunctionNode(name, parameters, body);
    }

    /**
     * Parses a function call node.
     * @return The node.
     */
    protected CallNode parseCall() {
        final var name = currentToken.getLiteral();
        nextToken();
        expectToken(TokenType.LPAREN);
        nextToken();

        final var arguments = new ArrayList<Node>();
        while (currentToken.getType() != TokenType.RPAREN) {
            arguments.add(expressionParser.parseExpression());
            if (currentToken.getType() == TokenType.COMMA && lexer.peekToken().getType() == TokenType.RPAREN) {
                expectToken(TokenType.COMMA);
            } else if (currentToken.getType() == TokenType.COMMA) {
                nextToken();
            } else {
                expectToken(TokenType.RPAREN);
            }
        }

        nextToken();

        return new CallNode(name, arguments);
    }

    /**
     * Parses a return statement.
     * @return The statement.
     */
    protected ReturnNode parseReturn() {
        nextToken();
        if (currentToken.getType() == TokenType.SEMICOLON) {
            nextToken();
            nextToken();
            return new ReturnNode(null);
        } else {
            final var value = expressionParser.parseExpression();
            expectToken(TokenType.SEMICOLON);
            nextToken();
            return new ReturnNode(value);
        }
    }

    /**
     * Parses the current token into a Node.
     * @param block The block node to which the node will be appended.
     */
    protected void parseToken(final BlockNode block) {
        switch (currentToken.getType()) {
            case IDENT -> {
                if (lexer.peekToken().getType() == TokenType.ASSIGN) {
                    block.addNode(parseVariableAssign());
                } else {
                    final var expression = expressionParser.parseExpression();

                    if (currentToken.getType() == TokenType.ASSIGN) {
                        nextToken();
                        final var value = expressionParser.parseExpression();
                        expectToken(TokenType.SEMICOLON);
                        nextToken();

                        block.addNode(new ArrayAssignNode(expression, value));
                    } else {
                        expectToken(TokenType.SEMICOLON);
                        nextToken();

                        block.addNode(new ExpressionStatementNode(expression));
                    }
                }
            }
            case VAR -> block.addNode(parseVariableDeclaration());
            case IF -> block.addNode(parseIf());
            case WHILE -> block.addNode(parseWhile());
            case RETURN -> block.addNode(parseReturn());
            case ILLEGAL -> Error.error("Illegal token `%s`.", currentToken.getLiteral());
            default -> {
                final var expression = new ExpressionStatementNode(expressionParser.parseExpression());
                nextToken();
                block.addNode(expression);
            }
        }
    }

    protected void parseGlobal(final FileNode file) {
        switch (currentToken.getType()) {
            case FUNC -> {
                final var function = parseFunction();
                if (function.getName().equals("main")) {
                    file.setMainFunction(function);
                } else {
                    file.addFunction(function);
                }
            }
            case VAR -> file.addVariable(parseVariableDeclaration());
            default -> expectToken(TokenType.FUNC);
        }
    }
}
