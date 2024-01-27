package io.github.syst3ms.skriptparser.structures;

import io.github.syst3ms.skriptparser.file.FileSection;
import io.github.syst3ms.skriptparser.log.SkriptLogger;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CompositeStructureLoader implements StructureLoader {

    private final Set<StructureLoader> loaders;

    public CompositeStructureLoader(Collection<StructureType<?>> structureTypes) {
        this.loaders = structureTypes.stream()
                .map(StructureType::createLoader)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean parse(@NotNull String script, @NotNull FileSection section, @NotNull SkriptLogger logger) {
        for (StructureLoader loader : loaders) {
            if (loader.parse(script, section, logger)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void loadAll(@NotNull String script, @NotNull SkriptLogger logger) {
        for (StructureLoader loader : loaders) {
            loader.loadAll(script, logger);
        }
    }
}