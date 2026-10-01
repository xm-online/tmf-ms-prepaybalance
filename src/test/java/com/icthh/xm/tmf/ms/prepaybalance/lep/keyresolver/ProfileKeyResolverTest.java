package com.icthh.xm.tmf.ms.prepaybalance.lep.keyresolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.icthh.xm.commons.lep.api.LepBaseKey;
import com.icthh.xm.lep.api.LepMethod;

import com.icthh.xm.tmf.ms.prepaybalance.utils.HeaderRequestExtractor;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.RequestContextHolder;

// Spring 7 no longer initializes @Mock fields in SpringExtension tests (MockitoTestExecutionListener is gone)
@ExtendWith(MockitoExtension.class)
class ProfileKeyResolverTest {

    @Mock
    private HeaderRequestExtractor headerRequestExtractor;

    @Mock
    private LepMethod lepMethod;

    @Mock
    private LepBaseKey lepBaseKey;

    @InjectMocks
    private ProfileKeyResolver profileKeyResolver;

    @BeforeEach
    void setUp() {
        // Clean up request context before each test
        RequestContextHolder.resetRequestAttributes();
    }

    // ProfileKeyResolver keeps the xm-commons default group(): the group of the LEP base key. These two tests used to
    // stub lepMethod.getParameter("group", String.class), which that default never read, and failed on master too
    @Test
    @DisplayName("Should return group of the LEP base key when group method is called")
    void shouldReturnGroupParameter() {
        // Given
        String expectedGroup = "testGroup";
        when(lepMethod.getLepBaseKey()).thenReturn(lepBaseKey);
        when(lepBaseKey.getGroup()).thenReturn(expectedGroup);

        // When
        String actualGroup = profileKeyResolver.group(lepMethod);

        // Then
        assertEquals(expectedGroup, actualGroup);
        verify(lepBaseKey).getGroup();
    }


    @Test
    @DisplayName("Should return empty string when group of the LEP base key is empty")
    void shouldReturnEmptyStringWhenGroupParameterIsEmpty() {
        // Given
        String expectedGroup = "";
        when(lepMethod.getLepBaseKey()).thenReturn(lepBaseKey);
        when(lepBaseKey.getGroup()).thenReturn(expectedGroup);

        // When
        String actualGroup = profileKeyResolver.group(lepMethod);

        // Then
        assertEquals(expectedGroup, actualGroup);
        verify(lepBaseKey).getGroup();
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
