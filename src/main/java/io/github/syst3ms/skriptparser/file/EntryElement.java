package io.github.syst3ms.skriptparser.file;

import lombok.Getter;

/**
 * A file element that represents an entry in a file. (aka a line with a key and a value)
 */
@Getter
public class EntryElement extends FileElement {

    public static final String SEPARATOR = ":";

    private final String key;
    private final String value;

    public EntryElement(String fileName, int line, String key, String value, int indentation) {
        super(fileName, line, key + ": " + value, indentation);
        this.key = key;
        this.value = value;
    }
}