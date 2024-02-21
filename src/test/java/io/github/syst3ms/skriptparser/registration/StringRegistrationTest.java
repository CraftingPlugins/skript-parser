package io.github.syst3ms.skriptparser.registration;

import io.github.syst3ms.skriptparser.TestRegistration;
import io.github.syst3ms.skriptparser.types.PatternType;
import io.github.syst3ms.skriptparser.types.TypeManager;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringRegistrationTest {

    @Test
    public void test0() {
        TestRegistration.register();

        Optional<PatternType<?>> textType = TypeManager.getPatternType("text");
        Optional<PatternType<?>> stringType = TypeManager.getPatternType("string");

        assertEquals(textType, stringType);
    }

}
