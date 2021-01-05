package com.icthh.xm.tmf.ms.prepaybalance.web.v2;

import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.prepaybalance.lep.keyresolver.ProfileKeyResolver;
import com.icthh.xm.tmf.ms.prepaybalance.web.v2.api.BalanceAdjustmentApiDelegate;
import com.icthh.xm.tmf.ms.prepaybalance.web.v2.api.model.BalanceAdjustmentBody;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@LepService(group = "service")
public class BalanceAdjustmentDelegate implements BalanceAdjustmentApiDelegate {

    @Timed
    @LogicExtensionPoint(value = "PrepayBalanceAdjustment", resolver = ProfileKeyResolver.class)
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.profile}, 'PREPAYBALANCE.BALANCE.ADJUSTMENT')")
    @Override
    @PrivilegeDescription("Privilege to adjust balance")
    public ResponseEntity<BalanceAdjustmentBody> createAdjustment(BalanceAdjustmentBody adjustmentBody) {
        return ResponseEntity.ok().build();
    }
}
