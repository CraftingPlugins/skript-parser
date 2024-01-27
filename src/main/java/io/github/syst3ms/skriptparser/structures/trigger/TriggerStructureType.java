package io.github.syst3ms.skriptparser.structures.trigger;

import io.github.syst3ms.skriptparser.lang.Trigger;
import io.github.syst3ms.skriptparser.structures.InternalStructureType;
import io.github.syst3ms.skriptparser.structures.StructureLoader;
import org.jetbrains.annotations.NotNull;

public class TriggerStructureType extends InternalStructureType<Trigger> {

    @Override
    public @NotNull StructureLoader createLoader() {
        return new TriggerStructureLoader(this);
    }

}