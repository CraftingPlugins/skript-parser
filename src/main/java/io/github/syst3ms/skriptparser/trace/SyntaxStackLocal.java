package io.github.syst3ms.skriptparser.trace;

import lombok.Getter;

import java.util.function.Supplier;

public class SyntaxStackLocal {

    @Getter
    private static SyntaxStack current;

    public static void push(SyntaxStack stack) {
        current = stack;
    }

    public static SyntaxStack pop() {
        SyntaxStack stack = current;
        current = null;
        return stack;
    }

    public static void run(SyntaxStack stack, Runnable runnable) {
        SyntaxStack previous = current;
        push(stack);
        try {
            runnable.run();
        } finally {
            if (previous != null)
                push(previous);
            else
                pop();
        }
    }

    public static <T> T supply(SyntaxStack stack, Supplier<? extends T> supplier) {
        SyntaxStack previous = current;
        push(stack);
        try {
            return supplier.get();
        } finally {
            if (previous != null)
                push(previous);
            else
                pop();
        }
    }

}
