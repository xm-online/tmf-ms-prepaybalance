package com.icthh.xm.tmf.ms.prepaybalance.config;

import com.icthh.xm.commons.config.client.service.TenantConfigService;
import com.icthh.xm.commons.lep.api.BaseLepContext;
import com.icthh.xm.commons.logging.trace.TraceService.TraceServiceField;
import com.icthh.xm.commons.permission.service.PermissionCheckService;
import com.icthh.xm.tmf.ms.prepaybalance.service.MailService;
import com.icthh.xm.tmf.ms.prepaybalance.service.SeparateTransactionExecutor;
import java.util.concurrent.Executor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

public class LepContext extends BaseLepContext implements TraceServiceField {

    public LepTemplates templates;
    public LepServices services;

    public static class LepTemplates {

        public RestTemplate rest;
        public KafkaTemplate<String, String> kafka;
        public JdbcTemplate jdbc;
    }

    public static class LepServices {

        public TenantConfigService tenantConfigService;
        public PermissionCheckService permissionService;
        public MailService mailService;
        public SeparateTransactionExecutor separateTransactionExecutor;
        public Executor asyncExecutor;

    }
}
