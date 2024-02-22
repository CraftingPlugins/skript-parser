package io.github.syst3ms.skriptparser.structures.options;

import io.github.syst3ms.skriptparser.file.EntryElement;
import io.github.syst3ms.skriptparser.file.FileElement;
import io.github.syst3ms.skriptparser.file.FileSection;
import io.github.syst3ms.skriptparser.file.VoidElement;
import io.github.syst3ms.skriptparser.log.ErrorType;
import io.github.syst3ms.skriptparser.log.SkriptLogger;
import io.github.syst3ms.skriptparser.structures.StructureLoader;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class OptionsStructureLoader implements StructureLoader {

    private final OptionsStructureType structureType;
    private OptionsStructure structure;

    @Override
    public boolean parse(@NotNull String script, @NotNull FileSection section, @NotNull SkriptLogger logger) {
        if (!section.getLineContent().equals("options"))
            return false;

        if (this.structure == null) {
            this.structure = new OptionsStructure();
            this.structureType.addStructure(script, this.structure);
        }

        logger.finalizeLogs();
        loadOptions(logger, section, "", structure);
        return true;
    }

    private void loadOptions(SkriptLogger logger, FileSection section, String prefix, OptionsStructure structure) {
        for (FileElement element : section.getElements()) {
            logger.nextLine();
            if (element instanceof VoidElement)
                return;

            if (element.isEntry()) {
                EntryElement entry = element.asEntry();
                structure.addOption(prefix + entry.getKey(), entry.getValue());
            } else if (element instanceof FileSection) {
                loadOptions(logger, (FileSection) element, prefix + element.getLineContent() + ".", structure);
            } else {
                logger.error("Unexpected element in options section: " + element.getLineContent(), ErrorType.SEMANTIC_ERROR);
            }
        }
    }

    @Override
    public void preloadAll(@NotNull String script, @NotNull SkriptLogger logger) {
        OptionsStructure.setCurrentOptions(structure);
    }

    @Override
    public void postLoadAll(@NotNull String script, @NotNull SkriptLogger logger) {
        OptionsStructure.setCurrentOptions(null);
    }
}
