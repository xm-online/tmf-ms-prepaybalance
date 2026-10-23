package com.icthh.xm.tmf.ms.prepaybalance.web.rest;

import static org.hamcrest.Matchers.startsWith;
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
 * Starts the whole application context and checks the OpenAPI document springdoc serves at /v3/api-docs
 * (springfox /v2/api-docs before the migration). No other test started the context before.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PrepaybalanceApp.class})
class ApiDocsIntTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void apiDocsIsServedAsOpenApi3() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.openapi").value(startsWith("3.")))
            .andExpect(jsonPath("$.paths['/tmf-api/prepayBalanceManagement/v2/bucket']").exists())
            .andExpect(jsonPath("$.paths['/tmf-api/prepayBalanceManagement/v4/transferBalance']").exists());
    }
}
