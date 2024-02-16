package io.github.syst3ms.skriptparser.structures.options;

import io.github.syst3ms.skriptparser.structures.Structure;
import io.github.syst3ms.skriptparser.util.StringUtils;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OptionsStructure implements Structure {

    private static final Pattern PATTERN = Pattern.compile("\\{@(.+?)\\}");
    @Setter
    private static OptionsStructure currentOptions;
    private final Map<String, String> options = new HashMap<>();

    public void addOption(@NotNull String name, @NotNull String value) {
        options.put(name, value);
    }

    public String replaceOptions(@NotNull String string) {
        return StringUtils.replaceAll(string, PATTERN, m -> {
            String option = options.get(m.group(1));
            if (option == null) {
                return m.group();
            }
            return Matcher.quoteReplacement(option);
        });
    }

    public static String replaceCurrentOptions(@NotNull String string) {
        if (currentOptions == null)
            return string;

        return currentOptions.replaceOptions(string);
    }

}
