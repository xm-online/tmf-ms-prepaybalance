package com.icthh.xm.tmf.ms.prepaybalance.web.v2;

import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.prepaybalance.lep.keyresolver.ProfileKeyResolver;
import com.icthh.xm.tmf.ms.prepaybalance.web.v2.api.BalanceDeductApiDelegate;
import com.icthh.xm.tmf.ms.prepaybalance.web.v2.api.model.BalanceDeductRequest;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@LepService(group = "service", name = "default")
public class BalanceDeductDelegate implements BalanceDeductApiDelegate {

    @Timed
    @LogicExtensionPoint(value = "PrepayBalanceDeduct", resolver = ProfileKeyResolver.class)
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.profile}, 'BALANCE.DEDUCT')")
    @Override
    @PrivilegeDescription("Privilege to deduct balance")
    public ResponseEntity<Void> deductBalanceAmount(BalanceDeductRequest balanceDeductRequest) {
        return ResponseEntity.ok().build();
    }
}
