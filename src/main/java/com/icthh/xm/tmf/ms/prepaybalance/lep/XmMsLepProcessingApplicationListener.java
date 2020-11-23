package com.icthh.xm.tmf.ms.prepaybalance.lep;

import static com.icthh.xm.tmf.ms.prepaybalance.lep.LepXmAccountMsConstants.BINDING_KEY_COMMONS;
import static com.icthh.xm.tmf.ms.prepaybalance.lep.LepXmAccountMsConstants.BINDING_KEY_JDBC;
import static com.icthh.xm.tmf.ms.prepaybalance.lep.LepXmAccountMsConstants.BINDING_KEY_SERVICES;
import static com.icthh.xm.tmf.ms.prepaybalance.lep.LepXmAccountMsConstants.BINDING_KEY_ASYNC_EXECUTOR;
import static com.icthh.xm.tmf.ms.prepaybalance.lep.LepXmAccountMsConstants.BINDING_KEY_TEMPLATES;
import static com.icthh.xm.tmf.ms.prepaybalance.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_PERMISSION_SERVICE;
import static com.icthh.xm.tmf.ms.prepaybalance.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_SERVICE_MAIL;
import static com.icthh.xm.tmf.ms.prepaybalance.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_SERVICE_SEPARATE_TRANSACTION_EXECUTOR;
import static com.icthh.xm.tmf.ms.prepaybalance.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_SERVICE_TENANT_CONFIG_SERVICE;
import static com.icthh.xm.tmf.ms.prepaybalance.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_TEMPLATE_KAFKA;
import static com.icthh.xm.tmf.ms.prepaybalance.lep.LepXmAccountMsConstants.BINDING_SUB_KEY_TEMPLATE_REST;

import com.icthh.xm.commons.config.client.service.TenantConfigService;
import com.icthh.xm.commons.lep.commons.CommonsExecutor;
import com.icthh.xm.commons.lep.commons.CommonsService;
import com.icthh.xm.commons.lep.spring.SpringLepProcessingApplicationListener;
import com.icthh.xm.commons.permission.service.PermissionCheckService;
import com.icthh.xm.lep.api.ScopedContext;
import com.icthh.xm.tmf.ms.prepaybalance.service.MailService;
import com.icthh.xm.tmf.ms.prepaybalance.service.SeparateTransactionExecutor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * The {@link XmMsLepProcessingApplicationListener} class.
 */
@RequiredArgsConstructor
public class XmMsLepProcessingApplicationListener extends SpringLepProcessingApplicationListener {


    private final TenantConfigService tenantConfigService;

    private final RestTemplate restTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final SeparateTransactionExecutor transactionExecutor;

    private final CommonsService commonsService;
    private final PermissionCheckService permissionCheckService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Executor asyncExecutor;
    private final MailService mailService;

    @Override
    protected void bindExecutionContext(ScopedContext executionContext) {
        // services
        Map<String, Object> services = new HashMap<>();
        services.put(BINDING_SUB_KEY_SERVICE_TENANT_CONFIG_SERVICE, tenantConfigService);
        services.put(BINDING_SUB_KEY_PERMISSION_SERVICE, permissionCheckService);
        services.put(BINDING_SUB_KEY_SERVICE_SEPARATE_TRANSACTION_EXECUTOR, transactionExecutor);
        services.put(BINDING_KEY_ASYNC_EXECUTOR, asyncExecutor);
        services.put(BINDING_SUB_KEY_SERVICE_MAIL, mailService);
        executionContext.setValue(BINDING_KEY_SERVICES, services);

        //commons
        executionContext.setValue(BINDING_KEY_COMMONS, new CommonsExecutor(commonsService));

        // templates
        Map<String, Object> templates = new HashMap<>();
        templates.put(BINDING_SUB_KEY_TEMPLATE_REST, restTemplate);
        templates.put(BINDING_SUB_KEY_TEMPLATE_KAFKA, kafkaTemplate);
        templates.put(BINDING_KEY_JDBC, jdbcTemplate);

        executionContext.setValue(BINDING_KEY_TEMPLATES, templates);
    }
}
