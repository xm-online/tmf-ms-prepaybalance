package com.icthh.xm.tmf.ms.prepaybalance.lep;

import com.icthh.xm.commons.config.client.service.TenantConfigService;
import com.icthh.xm.commons.lep.api.BaseLepContext;
import com.icthh.xm.commons.lep.api.LepContextFactory;
import com.icthh.xm.commons.permission.service.PermissionCheckService;
import com.icthh.xm.lep.api.LepMethod;
import com.icthh.xm.tmf.ms.prepaybalance.config.LepContext;
import com.icthh.xm.tmf.ms.prepaybalance.service.MailService;
import com.icthh.xm.tmf.ms.prepaybalance.service.SeparateTransactionExecutor;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class PrepaybalanceLepContextFactory implements LepContextFactory {

    private final TenantConfigService tenantConfigService;
    private final RestTemplate restTemplate;
    private final PermissionCheckService permissionCheckService;
    private final SeparateTransactionExecutor separateTransactionExecutor;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Executor asyncExecutor;
    private final MailService mailService;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public BaseLepContext buildLepContext(LepMethod lepMethod) {
        LepContext lepContext = new LepContext();
        lepContext.services = new LepContext.LepServices();
        lepContext.services.permissionService = permissionCheckService;
        lepContext.services.tenantConfigService = tenantConfigService;
        lepContext.services.separateTransactionExecutor = separateTransactionExecutor;
        lepContext.services.mailService = mailService;
        lepContext.templates = new LepContext.LepTemplates();
        lepContext.templates.rest = restTemplate;
        lepContext.templates.jdbc = jdbcTemplate;
        lepContext.templates.kafka = kafkaTemplate;
        lepContext.services.asyncExecutor = asyncExecutor;
        return lepContext;
    }
}
