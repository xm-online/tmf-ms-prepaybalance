package com.icthh.xm.tmf.ms.prepaybalance.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.icthh.xm.commons.i18n.error.domain.vm.ParameterizedErrorVM;
import com.icthh.xm.tmf.ms.prepaybalance.PrepaybalanceApp;
import com.icthh.xm.tmf.ms.prepaybalance.web.v2.api.model.BucketBalance;
import com.icthh.xm.tmf.ms.prepaybalance.web.v4.api.model.AdjustType;
import com.icthh.xm.tmf.ms.prepaybalance.web.v4.api.model.TransferBalanceCreate;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * Request and response JSON must stay as it was with Jackson 2 / openapi-generator 4.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PrepaybalanceApp.class})
class JacksonCompatibilityIntTest {

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    void modelKeepsDeclarationOrderAndCollectionDefaults() {
        BucketBalance model = new BucketBalance();
        model.setId("1");
        model.setBucketType("data");

        JsonNode json = jsonMapper.readTree(jsonMapper.writeValueAsString(model));

        assertThat(json.propertyNames()).containsExactly("id", "href", "name", "description", "bucketType",
            "remainedAmount", "reservedAmount", "validFor", "status", "product", "partyAccount", "realizingResource",
            "relatedParty");
        // required list: [] as generator 4.x initialized it; optional list: null
        assertThat(json.get("product").isArray()).isTrue();
        assertThat(json.get("product").isEmpty()).isTrue();
        assertThat(json.get("relatedParty").isNull()).isTrue();
    }

    @Test
    void absentFieldsKeepModelDefaults() {
        TransferBalanceCreate request = jsonMapper.readValue("{\"description\":\"d\"}", TransferBalanceCreate.class);

        assertThat(request.getDescription()).isEqualTo("d");
        assertThat(request.getProduct()).isNull();
        assertThat(request.getAmount()).isNull();
    }

    @Test
    void enumKeepsGenerator4ConstantName() {
        assertThat(AdjustType.ONETIME.getValue()).isEqualTo("oneTime");
    }

    @Test
    void businessErrorKeepsPropertyOrder() {
        JsonNode json = jsonMapper.readTree(jsonMapper.writeValueAsString(
            new ParameterizedErrorVM("error.code", "message", Map.of())));

        assertThat(json.propertyNames()).containsExactly("error", "error_description", "requestId", "params");
    }
}
