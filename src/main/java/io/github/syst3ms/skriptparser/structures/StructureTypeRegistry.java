package io.github.syst3ms.skriptparser.structures;

import io.github.syst3ms.skriptparser.lang.Trigger;
import io.github.syst3ms.skriptparser.structures.trigger.TriggerStructureType;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
@Getter
public class StructureTypeRegistry {


    public final StructureType<Trigger> TRIGGER = new TriggerStructureType();
    public final Set<StructureType<?>> TYPES = Set.of(
            TRIGGER
    );

    public StructureLoader createLoader() {
        return new CompositeStructureLoader(TYPES);
    }

}