package io.github.syst3ms.skriptparser.trace.impl;

import io.github.syst3ms.skriptparser.lang.Effect;
import io.github.syst3ms.skriptparser.registration.SyntaxInfo;
import io.github.syst3ms.skriptparser.trace.SyntaxStack;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EffectSyntaxStack implements SyntaxStack {

    private final String parsedString;
    private final SyntaxInfo<?> effect;
    private final SyntaxStack parent;

    public String getParsedString() {
        return "(effect) " + parsedString;
    }

    @Override
    public String getSyntaxDisplay() {
        return effect.getSyntaxClass().getSimpleName();
    }

}
