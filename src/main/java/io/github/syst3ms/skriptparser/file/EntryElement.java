package io.github.syst3ms.skriptparser.file;

import lombok.Getter;

/**
 * A file element that represents an entry in a file. (aka a line with a key and a value)
 */
@Getter
public class EntryElement extends FileElement {

    public static final String SEPARATOR = ":";
    public static final String ALIASES_SEPARATOR = "=";

    private final String key;
    private final String value;

    public EntryElement(String fileName, int line, String key, String value, String content, int indentation) {
        super(fileName, line, content, indentation);
        this.key = key;
        this.value = value;
    }
}