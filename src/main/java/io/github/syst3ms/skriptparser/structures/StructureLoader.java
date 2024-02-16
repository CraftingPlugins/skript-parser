package io.github.syst3ms.skriptparser.structures;

import io.github.syst3ms.skriptparser.file.FileSection;
import io.github.syst3ms.skriptparser.log.SkriptLogger;
import org.jetbrains.annotations.NotNull;

public interface StructureLoader {

    /**
     * Parses a section of a file into a structure and stores it
     *
     * @param section the section to parse
     * @param logger the logger to log errors to
     * @return {@code true} if the parsing was successful, {@code false} otherwise
     */
    boolean parse(@NotNull String script, @NotNull FileSection section, @NotNull SkriptLogger logger);

    /**
     * Preloads all the structures of this type
     *
     * @param script the name of the script
     * @param logger the logger to log errors to
     */
    default void preloadAll(@NotNull String script, @NotNull SkriptLogger logger) {
        // to be overridden
    }

    /**
     * Loads all the structures of this type
     *
     * @param logger the logger to log errors to
     */
    default void loadAll(@NotNull String script, @NotNull SkriptLogger logger) {
        // to be overridden
    }

    /**
     * Post-loads all the structures of this type
     *
     * @param script the name of the script
     * @param logger the logger to log errors to
     */
    default void postLoadAll(@NotNull String script, @NotNull SkriptLogger logger) {
        // to be overridden
    }

}
