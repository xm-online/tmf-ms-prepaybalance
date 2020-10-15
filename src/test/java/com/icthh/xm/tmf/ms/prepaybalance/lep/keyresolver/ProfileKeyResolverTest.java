package com.icthh.xm.tmf.ms.prepaybalance.lep.keyresolver;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.icthh.xm.commons.lep.XmLepConstants;
import com.icthh.xm.commons.lep.spring.LepServiceHandler;
import com.icthh.xm.lep.api.LepKey;
import com.icthh.xm.lep.api.LepKeyResolver;
import com.icthh.xm.lep.api.LepManager;
import com.icthh.xm.lep.api.LepMethod;
import com.icthh.xm.lep.api.Version;
import com.icthh.xm.lep.core.CoreLepManager;
import com.icthh.xm.tmf.ms.prepaybalance.utils.HeaderRequestExtractor;
import com.icthh.xm.tmf.ms.prepaybalance.web.v2.BalanceDeductDelegate;
import com.icthh.xm.tmf.ms.prepaybalance.web.v2.api.model.BalanceDeductRequest;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(SpringExtension.class)
class ProfileKeyResolverTest {
    private static final String PROFILE_KEY = "profile";
    private static final String PROFILE_VALUE = "TEST-PROFILE";
    private static final String PROFILE_VALUE_RESOLVED = "TEST_PROFILE";

    @InjectMocks
    private LepServiceHandler lepServiceHandler;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private CoreLepManager lepManager;

    @Captor
    private ArgumentCaptor<LepKey> baseLepKey;

    @Captor
    private ArgumentCaptor<LepKeyResolver> keyResolver;

    @Captor
    private ArgumentCaptor<LepMethod> lepMethod;

    @Captor
    private ArgumentCaptor<Version> version;

    @Test
    void shouldResolveLepByHeader() throws Throwable {

        Method method = BalanceDeductDelegate.class.getMethod("deductBalanceAmount", BalanceDeductRequest.class);

        when(applicationContext.getBean(LepManager.class)).thenReturn(lepManager);

        HeaderRequestExtractor headerRequestExtractor = new HeaderRequestExtractor();
        ProfileKeyResolver resolver = new ProfileKeyResolver(headerRequestExtractor);
        when(applicationContext.getBean(ProfileKeyResolver.class)).thenReturn(resolver);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(PROFILE_KEY, PROFILE_VALUE);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        lepServiceHandler.onMethodInvoke(BalanceDeductDelegate.class,
            new BalanceDeductDelegate(), method, new Object[]{null});

        verify(lepManager)
            .processLep(baseLepKey.capture(), version.capture(), keyResolver.capture(), lepMethod.capture());

        LepKey resolvedKey = resolver.resolve(baseLepKey.getValue(), lepMethod.getValue(), null);

        assertEquals(
            String.join(XmLepConstants.EXTENSION_KEY_SEPARATOR,
                "service", "PrepayBalanceDeduct", PROFILE_VALUE_RESOLVED), resolvedKey.getId());
    }
}
