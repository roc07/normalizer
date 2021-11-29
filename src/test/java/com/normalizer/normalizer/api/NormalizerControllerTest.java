package com.normalizer.normalizer.api;

import com.normalizer.normalizer.features.normalizer.dto.NormalizedJobTitleDto;
import com.normalizer.normalizer.features.normalizer.exception.InvalidJobTitleException;
import com.normalizer.normalizer.features.normalizer.service.NormalizedJobTitle;
import com.normalizer.normalizer.features.normalizer.service.NormalizerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NormalizerControllerTest {

    private static final String NULL_EXCEPTION = "null value given";
    private static final String ARCHITECT = "architect";

    NormalizerService normalizerServiceMock = mock(NormalizerService.class);
    NormalizerController controller = new NormalizerController();

    @BeforeEach
    void setUp() {
        controller.setNormalizerService(normalizerServiceMock);
    }

    @Test
    void normalizeSingleJobTitle_invalidValue_badRequest() {
        when(normalizerServiceMock.normalizeJobTitle(null)).thenThrow(new InvalidJobTitleException(NULL_EXCEPTION));

        ResponseEntity<?> result = controller.normalizeSingleJobTitle(null);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(NULL_EXCEPTION, result.getBody());
    }

    @Test
    void normalizeSingleJobTitle_validValue_ok() {
        when(normalizerServiceMock.normalizeJobTitle(ARCHITECT))
                .thenReturn(new NormalizedJobTitleDto(NormalizedJobTitle.ARCHITECT.getNormalizedTitle(), 1));

        ResponseEntity<?> result = controller.normalizeSingleJobTitle(ARCHITECT);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(NormalizedJobTitle.ARCHITECT.getNormalizedTitle(),
                ((NormalizedJobTitleDto) Objects.requireNonNull(result.getBody())).getNormalizedJobTitle());
    }
}
