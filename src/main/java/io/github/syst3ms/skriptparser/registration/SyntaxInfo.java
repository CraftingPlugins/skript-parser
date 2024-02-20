package io.github.syst3ms.skriptparser.registration;

import io.github.syst3ms.skriptparser.lang.Expression;
import io.github.syst3ms.skriptparser.lang.SkriptEvent;
import io.github.syst3ms.skriptparser.lang.SyntaxElement;
import io.github.syst3ms.skriptparser.pattern.PatternElement;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A class containing info about a {@link SyntaxElement} that isn't an {@link Expression} or an {@link SkriptEvent}
 * @param <C> the {@link SyntaxElement} class
 */
public class SyntaxInfo<C> {
    private final Class<C> syntaxClass;
    private final Supplier<C> syntaxSupplier;
    private final List<PatternElement> patterns;
    private final int priority;
    private final SkriptAddon registerer;
    protected final Map<String, Object> data;

    public SyntaxInfo(SkriptAddon registerer, Class<C> syntaxClass, int priority, List<PatternElement> patterns) {
        this(registerer, syntaxClass, priority, patterns, new HashMap<>());
    }

    public SyntaxInfo(SkriptAddon registerer, Class<C> syntaxClass, int priority, List<PatternElement> patterns, Map<String, Object> data) {
        this(registerer, syntaxClass, priority, patterns, data, null);
    }

    public SyntaxInfo(SkriptAddon registerer, Class<C> syntaxClass, int priority, List<PatternElement> patterns, Map<String, Object> data, @Nullable Supplier<C> syntaxSupplier) {
        this.syntaxClass = syntaxClass;
        this.syntaxSupplier = syntaxSupplier != null ? syntaxSupplier : () -> {
            try {
                return syntaxClass.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
        this.patterns = patterns;
        this.priority = priority;
        this.registerer = registerer;
        this.data = data;
    }

    public SkriptAddon getRegisterer() {
        return registerer;
    }

    public Class<C> getSyntaxClass() {
        return syntaxClass;
    }

    public C createSyntax() {
        return syntaxSupplier.get();
    }

    public int getPriority() {
        return priority;
    }

    public List<PatternElement> getPatterns() {
        return patterns;
    }

    /**
     * Retrieves a data instance by its identifier.
     * @param identifier the identifier
     * @param type the expected data type
     * @return the data instance
     */
    public <T> T getData(String identifier, Class<T> type) {
        return type.cast(data.get(identifier));
    }
}
