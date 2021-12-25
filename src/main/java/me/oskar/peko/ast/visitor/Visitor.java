package me.oskar.peko.ast.visitor;

import me.oskar.peko.ast.*;

public interface Visitor {

    void visit(final ArrayAccessNode arrayAccessNode);

    void visit(final ArrayAssignNode arrayAssignNode);

    void visit(final ArrayNode arrayNode);

    void visit(final BinaryOperatorNode binaryOperatorNode);

    void visit(final BlockNode blockNode);

    void visit(final BooleanNode booleanNode);

    void visit(final CallNode callNode);

    void visit(final ExpressionStatementNode expressionStatementNode);

    void visit(final FileNode fileNode);

    void visit(final FunctionNode functionNode);

    void visit(final IdentNode identNode);

    void visit(final IfNode ifNode);

    void visit(final NilNode nilNode);

    void visit(final NumberNode numberNode);

    void visit(final ParameterDeclarationNode parameterDeclarationNode);

    void visit(final ReturnNode returnNode);

    void visit(final StringNode stringNode);

    void visit(final UnaryOperatorNode unaryOperatorNode);

    void visit(final VariableAssignNode variableAssignNode);

    void visit(final VariableDeclarationNode variableDeclarationNode);

    void visit(final WhileNode whileNode);
}
