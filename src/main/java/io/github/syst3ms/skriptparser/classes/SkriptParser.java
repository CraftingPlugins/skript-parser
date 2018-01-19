package io.github.syst3ms.skriptparser.classes;

import io.github.syst3ms.skriptparser.pattern.ChoiceElement;
import io.github.syst3ms.skriptparser.pattern.ChoiceGroup;
import io.github.syst3ms.skriptparser.pattern.CompoundElement;
import io.github.syst3ms.skriptparser.pattern.ExpressionElement;
import io.github.syst3ms.skriptparser.pattern.OptionalGroup;
import io.github.syst3ms.skriptparser.pattern.PatternElement;
import io.github.syst3ms.skriptparser.pattern.RegexGroup;
import io.github.syst3ms.skriptparser.pattern.TextElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A parser instance used for matching a pattern to a syntax, stores a parse mark
 */
public class SkriptParser {
	private String pattern;
    private PatternElement element;
    private int patternIndex = 0;
	private List<Expression<?>> parsedExpressions = new ArrayList<>();
	private List<String> regexMatches = new ArrayList<>();
	private int parseMark = 0;

	public SkriptParser(PatternElement e) {
		this.pattern = e.toString();
        element = e;
    }

    public String getPattern() {
        return pattern;
    }

    public PatternElement getElement() {
        return element;
    }

    public int getPatternIndex() {
        return patternIndex;
    }

    public void advanceInPattern() {
        patternIndex++;
    }

	public List<Expression<?>> getParsedExpressions() {
		return parsedExpressions;
	}

    public void addExpression(Expression<?> expression) {
        parsedExpressions.add(expression);
    }

	public List<String> getRegexMatches() {
		return regexMatches;
	}

    public void addRegexMatch(String match) {
        regexMatches.add(match);
    }

	public int getParseMark() {
		return parseMark;
	}

	public void addMark(int mark) {
		parseMark ^= mark;
	}

	public List<PatternElement> flatten(PatternElement element) {
	    List<PatternElement> flattened = new ArrayList<>();
	    if (element instanceof CompoundElement) {
            for (PatternElement e : ((CompoundElement) element).getElements()) {
                flattened.addAll(flatten(e));
            }
            return flattened;
        } else {
	        flattened.add(element);
	        return flattened;
        }
    }

    public List<PatternElement> getPossibleInputs(List<PatternElement> elements) {
	    List<PatternElement> possibilities = new ArrayList<>();
        for (PatternElement element : elements) {
            if (element instanceof TextElement || element instanceof RegexGroup) {
                if (element instanceof TextElement && ((TextElement) element).getText().matches("\\s+"))
                    continue;
                possibilities.add(element);
                return possibilities;
            } else if (element instanceof ChoiceGroup) {
                for (ChoiceElement choice : ((ChoiceGroup) element).getChoices()) {
                    possibilities.addAll(getPossibleInputs(flatten(choice.getElement())));
                }
                return possibilities;
            } else if (element instanceof ExpressionElement) { // Can't do much about this
                possibilities.add(new RegexGroup(Pattern.compile(".+")));
                return possibilities;
            } else if (element instanceof OptionalGroup) {
                possibilities.addAll(getPossibleInputs(flatten(((OptionalGroup) element).getElement())));
            }
        }
        return new ArrayList<>();
    }

    public Expression<?> parseExpression(String s) { // empty implementation
	    return null;
    }
}
