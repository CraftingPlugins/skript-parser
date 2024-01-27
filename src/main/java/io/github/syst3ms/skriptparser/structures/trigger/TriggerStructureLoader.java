package io.github.syst3ms.skriptparser.structures.trigger;

import io.github.syst3ms.skriptparser.file.FileSection;
import io.github.syst3ms.skriptparser.lang.UnloadedTrigger;
import io.github.syst3ms.skriptparser.log.SkriptLogger;
import io.github.syst3ms.skriptparser.parsing.ParserState;
import io.github.syst3ms.skriptparser.parsing.SyntaxParser;
import io.github.syst3ms.skriptparser.structures.StructureLoader;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TriggerStructureLoader implements StructureLoader {

    private final List<UnloadedTrigger> unloadedTriggers = new ArrayList<>();
    private final TriggerStructureType structureType;
    @Override
    public boolean parse(@NotNull String script, @NotNull FileSection section, @NotNull SkriptLogger logger) {
        var optional = SyntaxParser.parseTrigger(section, logger);
        if (optional.isPresent()) {
            var trigger = optional.get();

            unloadedTriggers.add(trigger);
            return true;
        }

        return false;
    }

    @Override
    public void loadAll(@NotNull String script, @NotNull SkriptLogger logger) {
        unloadedTriggers.sort((a, b) -> b.getTrigger().getEvent().getLoadingPriority() - a.getTrigger().getEvent().getLoadingPriority());
        for (var unloaded : unloadedTriggers) {
            logger.finalizeLogs();
            logger.setLine(unloaded.getLine());

            var loaded = unloaded.getTrigger();
            ParserState state = unloaded.getParserState();
            loaded.loadSection(unloaded.getSection(), state, logger);
            unloaded.getEventInfo().getRegisterer().handleTrigger(loaded);

            structureType.addStructure(script, loaded);
        }
    }
}