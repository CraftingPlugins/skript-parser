package io.github.syst3ms.skriptparser.trace.impl;

import io.github.syst3ms.skriptparser.registration.ExpressionInfo;
import io.github.syst3ms.skriptparser.trace.SyntaxStack;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ExpressionSyntaxStack implements SyntaxStack {

    private final String parsedString;
    private final ExpressionInfo<?, ?> expressionInfo;
    private final SyntaxStack parent;

    public String getParsedString() {
        return "(expr) " + parsedString;
    }

    @Override
    public String getSyntaxDisplay() {
        return expressionInfo.getSyntaxClass().getSimpleName();
    }
}
