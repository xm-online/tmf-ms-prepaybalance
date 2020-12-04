package com.icthh.xm.tmf.ms.prepaybalance.web.v2;

import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.prepaybalance.lep.keyresolver.ProfileKeyResolver;
import com.icthh.xm.tmf.ms.prepaybalance.web.v2.api.BalanceReserveApiDelegate;
import com.icthh.xm.tmf.ms.prepaybalance.web.v2.api.model.BalanceReserveBody;
import com.icthh.xm.tmf.ms.prepaybalance.web.v2.api.model.BalanceReserveRequest;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@LepService(group = "service")
public class BalanceReserveDelegate implements BalanceReserveApiDelegate {

    @Timed
    @LogicExtensionPoint(value = "PrepayBalanceReserve", resolver = ProfileKeyResolver.class)
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.profile}, 'PREPAYBALANCE.BALANCE.RESERVE')")
    @Override
    @PrivilegeDescription("Privilege to create reserve balance operation")
    public ResponseEntity<BalanceReserveBody> createReserveOperation(BalanceReserveRequest balanceReserveRequest) {
        return ResponseEntity.ok().build();
    }
}

