package com.icthh.xm.tmf.ms.prepaybalance.web.rest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.icthh.xm.commons.i18n.error.web.ExceptionTranslator;
import com.icthh.xm.tmf.ms.prepaybalance.PrepaybalanceApp;
import com.icthh.xm.tmf.ms.prepaybalance.config.SecurityBeanOverrideConfiguration;
import com.icthh.xm.tmf.ms.prepaybalance.web.errors.LegacyErrorResponseAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.WebApplicationContext;

/**
 * Error responses that must stay as they were before the migration (compared with master).
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PrepaybalanceApp.class})
class LegacyErrorResponseIntTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private LegacyErrorResponseAdvice legacyErrorResponseAdvice;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    void unmappedPathIsNotFound() throws Exception {
        mockMvc.perform(get("/tmf-api/nothing").header("x-tenant", "XM"))
            .andExpect(status().isNotFound());
    }

    @Test
    void missingParameterKeepsSpring5Message() throws Exception {
        mockMvc.perform(get("/tmf-api/prepayBalanceManagement/v2/accumulatedbalance").header("x-tenant", "XM"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("error.validation"))
            .andExpect(jsonPath("$.fieldErrors[0].field").value("name"))
            .andExpect(jsonPath("$.fieldErrors[0].message").value("Required String parameter 'name' is not present"));
    }

    @Test
    void upstreamServerErrorKeepsItsStatus() throws Exception {
        // a LEP calling lepContext.templates.rest gets a 5xx: answered with the upstream status, as before
        MockMvc upstreamMockMvc = MockMvcBuilders.standaloneSetup(new UpstreamErrorController())
            .setControllerAdvice(legacyErrorResponseAdvice, exceptionTranslator)
            .build();
        upstreamMockMvc.perform(get("/test/legacy-errors/upstream"))
            .andExpect(status().is(HttpStatus.BAD_GATEWAY.value()))
            .andExpect(jsonPath("$.error").value("error.502"));
    }

    @RestController
    static class UpstreamErrorController {

        @GetMapping("/test/legacy-errors/upstream")
        public void upstream() {
            throw new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "bad gateway");
        }
    }
}
