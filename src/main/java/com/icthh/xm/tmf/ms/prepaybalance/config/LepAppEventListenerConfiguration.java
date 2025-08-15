package com.icthh.xm.tmf.ms.prepaybalance.config;

import com.icthh.xm.commons.config.client.service.TenantConfigService;
import com.icthh.xm.commons.permission.service.PermissionCheckService;
import com.icthh.xm.tmf.ms.prepaybalance.lep.PrepaybalanceLepContextFactory;
import com.icthh.xm.tmf.ms.prepaybalance.service.MailService;
import com.icthh.xm.tmf.ms.prepaybalance.service.SeparateTransactionExecutor;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * The {@link LepAppEventListenerConfiguration} class.
 */
@Configuration
public class LepAppEventListenerConfiguration {

    @Bean
    PrepaybalanceLepContextFactory buildLepProcessingApplicationListener(
        TenantConfigService tenantConfigService,
        @Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate,
        PermissionCheckService permissionCheckService,
        KafkaTemplate<String, String> kafkaTemplate,
        JdbcTemplate jdbcTemplate,
        SeparateTransactionExecutor separateTransactionExecutor,
        @Qualifier("taskExecutor") Executor asyncExecutor,
        MailService mailService) {

        return new PrepaybalanceLepContextFactory(
            tenantConfigService,
            restTemplate,
            permissionCheckService,
            separateTransactionExecutor,
            kafkaTemplate,
            asyncExecutor,
            mailService,
            jdbcTemplate);
    }

}
