package io.github.syst3ms.skriptparser.structures;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

public interface StructureType<S extends Structure> {

    Collection<S> getAllStructures();

    Set<S> getStructuresByScript(@NotNull String scriptName);

    void detachStructuresByScript(@NotNull String scriptName);

    @NotNull StructureLoader createLoader();

}
