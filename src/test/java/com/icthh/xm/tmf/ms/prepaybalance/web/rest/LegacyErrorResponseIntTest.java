package com.icthh.xm.tmf.ms.prepaybalance.web.rest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.icthh.xm.tmf.ms.prepaybalance.PrepaybalanceApp;
import com.icthh.xm.tmf.ms.prepaybalance.config.SecurityBeanOverrideConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Error responses that must stay as they were before the migration (compared with master).
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PrepaybalanceApp.class})
class LegacyErrorResponseIntTest {

    @Autowired
    private WebApplicationContext context;

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
}
