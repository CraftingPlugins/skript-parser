package io.github.syst3ms.skriptparser.registration.tags;

import lombok.Getter;

import java.util.function.Supplier;

/**
 * A class containing info about a {@link Tag}.
 * @param <C> the {@link Tag} class
 * @author Mwexim
 */
public class TagInfo<C extends Tag> {
	private final Class<C> c;
	@Getter
	private final Supplier<C> supplier;
	private final int priority;

	public TagInfo(Class<C> c, Supplier<C> supplier, int priority) {
		this.c = c;
		this.supplier = supplier;
		this.priority = priority;
	}

	public TagInfo(Class<C> c, int priority) {
		this(c, null, priority);
	}

	public Class<C> getSyntaxClass() {
		return c;
	}

	public int getPriority() {
		return priority;
	}
}
