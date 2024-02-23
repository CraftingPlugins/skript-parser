package io.github.syst3ms.skriptparser.log;

import io.github.syst3ms.skriptparser.trace.SyntaxStack;
import io.github.syst3ms.skriptparser.trace.SyntaxStackLocal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * An entry in Skript's log.
 */
@RequiredArgsConstructor
public class LogEntry {
    private final LogType type;
    private final String message;
    private final int line;
    private final List<ErrorContext> errorContext;
    private final ErrorType errorType;
    private final String tip;
    @Getter
    @Nullable
    private final SyntaxStack syntaxStack;
    @Getter
    private final Throwable throwable;

    public LogEntry(String message, LogType verbosity, int line, List<ErrorContext> errorContext, @Nullable ErrorType errorType) {
        this(message, verbosity, line, errorContext, errorType, null);
    }

    public LogEntry(String message, LogType verbosity, int line, List<ErrorContext> errorContext, @Nullable ErrorType errorType, @Nullable String tip) {
        this(message, verbosity, line, errorContext, errorType, tip, new Throwable());
    }

    public LogEntry(String message, LogType verbosity, int line, List<ErrorContext> errorContext, @Nullable ErrorType errorType, @Nullable String tip, @Nullable Throwable throwable) {
        this(verbosity, message, line, errorContext, errorType, tip, SyntaxStackLocal.getCurrent(), throwable);
    }

    public String getMessage() {
        return message;
    }

    public LogType getType() {
        return type;
    }

    List<ErrorContext> getErrorContext() {
        return errorContext;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public Optional<String> getTip() {
        return Optional.ofNullable(tip);
    }

    public int getLine() {
        return line;
    }
}
