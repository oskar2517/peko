package me.oskar.ast;

public class IfNode extends Node {

    private final Node condition;
    private final BlockNode consequence;
    private final BlockNode alternative;

    public IfNode(final Node condition, final BlockNode consequence, final BlockNode alternative) {
        this.condition = condition;
        this.consequence = consequence;
        this.alternative = alternative;
    }

    public Node getCondition() {
        return condition;
    }

    public BlockNode getConsequence() {
        return consequence;
    }

    public BlockNode getAlternative() {
        return alternative;
    }

    @Override
    public String toString() {
        return String.format("(IF %s %s %s)", condition, consequence, alternative);
    }
}
