package com.normalizer.normalizer.features.normalizer.service;

import com.normalizer.normalizer.features.normalizer.dto.NormalizedJobTitleDto;
import com.normalizer.normalizer.features.normalizer.exception.InvalidJobTitleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class NormalizerServiceTest {

    NormalizerService normalizerService = new NormalizerService();

    @Test
    void normalizeJobTitle_null_throwsInvalidJobTitleException() {
        Throwable exception = assertThrows(InvalidJobTitleException.class,
                () -> normalizerService.normalizeJobTitle(null));

        assertEquals("null value given", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "",
                    "   ",
                    "123213",
                    "()/.\\$%%@!&"
            }
    )
    void normalizeJobTitle_invalidJobTitle_throwsInvalidJobTitleException(String title) {
        Throwable exception = assertThrows(InvalidJobTitleException.class,
                () -> normalizerService.normalizeJobTitle(title));

        assertEquals(String.format("Invalid job title: %s", title), exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("prepareHighScoreTitles")
    void normalizeJobTitle_validJobTitleNoAmbiguity_returnsAccountantFullScore(String title) {
        NormalizedJobTitleDto normalizedTitleDto = normalizerService.normalizeJobTitle(title);

        assertEquals(NormalizedJobTitle.ACCOUNTANT.getNormalizedTitle(), normalizedTitleDto.getNormalizedJobTitle());
        assertEquals(1, normalizedTitleDto.getQualityScore());
    }

    private static Stream<String> prepareHighScoreTitles() {
        return Stream.of(
                "example" + NormalizedJobTitle.ACCOUNTANT.getNormalizedTitle() + "example",
                NormalizedJobTitle.ACCOUNTANT.getNormalizedTitle() + NormalizedJobTitle.ACCOUNTANT.getNormalizedTitle() ,
                NormalizedJobTitle.ACCOUNTANT.getNormalizedTitle() + NormalizedJobTitle.ACCOUNTANT.getNormalizedTitle(),
                NormalizedJobTitle.ACCOUNTANT.getKeyWord(),
                "ACCOUNTANTS",
                "AC1COUNT9AN...TS  asd",
                "archiAcACCOUNTANTengin"
        );
    }

    @ParameterizedTest
    @MethodSource("prepareLowScoreTitles")
    void normalizeJobTitle_validJobTitleAmbiguity_returnsArchitectLowerScore(String title) {
        NormalizedJobTitleDto normalizedTitleDto = normalizerService.normalizeJobTitle(title);

        assertEquals(NormalizedJobTitle.ARCHITECT.getNormalizedTitle(), normalizedTitleDto.getNormalizedJobTitle());
        assertTrue(normalizedTitleDto.getQualityScore() < 1);
    }

    private static Stream<String> prepareLowScoreTitles() {
        return Stream.of(
                "example" + NormalizedJobTitle.ARCHITECT.getNormalizedTitle() +
                        "example" + NormalizedJobTitle.QUANTITY_SURVEYOR.getNormalizedTitle(),
                "Java" + NormalizedJobTitle.ARCHITECT.getNormalizedTitle() + "Accountant",
                NormalizedJobTitle.ARCHITECT.getKeyWord() + NormalizedJobTitle.ACCOUNTANT.getKeyWord(),
                "c#architect-engineer",
                "Ar1chi---TEcTed(quantitysurveyor)",
                NormalizedJobTitle.ARCHITECT.getNormalizedTitle() +
                        NormalizedJobTitle.SOFTWARE_ENGINEER.getNormalizedTitle() +
                        NormalizedJobTitle.QUANTITY_SURVEYOR.getNormalizedTitle() +
                        NormalizedJobTitle.ACCOUNTANT.getNormalizedTitle()
        );
    }

}
