package com.icthh.xm.tmf.ms.prepaybalance.lep.keyresolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.icthh.xm.lep.api.LepMethod;

import com.icthh.xm.tmf.ms.prepaybalance.utils.HeaderRequestExtractor;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;

@ExtendWith(SpringExtension.class)
class ProfileKeyResolverTest {

    private static final String GROUP_PARAMETER = "group";

    @Mock
    private HeaderRequestExtractor headerRequestExtractor;

    @Mock
    private LepMethod lepMethod;

    @InjectMocks
    private ProfileKeyResolver profileKeyResolver;

    @BeforeEach
    void setUp() {
        // Clean up request context before each test
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    @DisplayName("Should return group parameter when group method is called")
    void shouldReturnGroupParameter() {
        // Given
        String expectedGroup = "testGroup";
        when(lepMethod.getParameter(GROUP_PARAMETER, String.class)).thenReturn(expectedGroup);

        // When
        String actualGroup = profileKeyResolver.group(lepMethod);

        // Then
        assertEquals(expectedGroup, actualGroup);
        verify(lepMethod).getParameter(GROUP_PARAMETER, String.class);
    }


    @Test
    @DisplayName("Should return empty string when group parameter is empty")
    void shouldReturnEmptyStringWhenGroupParameterIsEmpty() {
        // Given
        String expectedGroup = "";
        when(lepMethod.getParameter(GROUP_PARAMETER, String.class)).thenReturn(expectedGroup);

        // When
        String actualGroup = profileKeyResolver.group(lepMethod);

        // Then
        assertEquals(expectedGroup, actualGroup);
        verify(lepMethod).getParameter(GROUP_PARAMETER, String.class);
    }

    @Test
    @DisplayName("Should return list with profile when segments method is called")
    void shouldReturnListWithProfile() {
        // Given
        String expectedProfile = "testProfile";
        when(headerRequestExtractor.getProfile()).thenReturn(expectedProfile);

        // When
        List<String> actualSegments = profileKeyResolver.segments(lepMethod);

        // Then
        assertNotNull(actualSegments);
        assertEquals(1, actualSegments.size());
        assertEquals(expectedProfile, actualSegments.get(0));
        verify(headerRequestExtractor).getProfile();
    }
}
