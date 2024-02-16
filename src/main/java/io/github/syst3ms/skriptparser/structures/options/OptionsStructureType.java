package io.github.syst3ms.skriptparser.structures.options;

import io.github.syst3ms.skriptparser.structures.InternalStructureType;
import io.github.syst3ms.skriptparser.structures.StructureLoader;
import org.jetbrains.annotations.NotNull;

public class OptionsStructureType extends InternalStructureType<OptionsStructure> {
    @Override
    public @NotNull StructureLoader createLoader() {
        return new OptionsStructureLoader(this);
    }
}
