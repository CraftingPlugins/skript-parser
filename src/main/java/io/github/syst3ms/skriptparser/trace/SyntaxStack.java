package io.github.syst3ms.skriptparser.trace;

public interface SyntaxStack {

    String getParsedString();

    SyntaxStack getParent();

    public abstract String getSyntaxDisplay();

    default String string() {
        StringBuilder builder = new StringBuilder();
        SyntaxStack parent = this.getParent();
        if (parent != null)
            builder.append(parent.string()).append(" -> ");
        builder
                .append(getParsedString())
                .append(" (")
                .append(getSyntaxDisplay())
                .append(")");

        return builder.toString();
    }

}
