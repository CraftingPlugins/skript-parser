package io.github.syst3ms.skriptparser.structures;

import com.google.common.collect.HashMultimap;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

public abstract class InternalStructureType<S extends Structure> implements StructureType<S> {

    private final HashMultimap<String, S> structuresByType = HashMultimap.create();

    @Override
    public Collection<S> getAllStructures() {
        return this.structuresByType.values();
    }

    @Override
    public Set<S> getStructuresByScript(@NotNull String scriptName) {
        return this.structuresByType.get(scriptName);
    }

    public void addStructure(@NotNull String scriptName, @NotNull S structure) {
        this.structuresByType.put(scriptName, structure);
    }

    @Override
    public void detachStructuresByScript(@NotNull String scriptName) {
        this.structuresByType.removeAll(scriptName);
    }

}