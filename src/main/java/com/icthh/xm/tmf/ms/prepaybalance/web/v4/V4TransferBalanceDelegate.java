package com.icthh.xm.tmf.ms.prepaybalance.web.v4;

import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.prepaybalance.web.v4.api.V4TransferBalanceApiDelegate;
import com.icthh.xm.tmf.ms.prepaybalance.web.v4.api.model.TransferBalance;
import com.icthh.xm.tmf.ms.prepaybalance.web.v4.api.model.TransferBalanceCreate;
import io.micrometer.core.annotation.Timed;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@LepService(group = "service")
public class V4TransferBalanceDelegate implements V4TransferBalanceApiDelegate {

    @Timed
    @Override
    @LogicExtensionPoint(value = "CreateTransferBalance")
    @PrivilegeDescription("Privilege to transfer balance")
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.profile}, 'PREPAYBALANCE.BALANCE.TRANSFER')")
    public ResponseEntity<TransferBalance> createTransferBalance(TransferBalanceCreate transferBalance) {
        return ResponseEntity.ok().build();
    }

    @Timed
    @Override
    @LogicExtensionPoint(value = "ListTransferBalance")
    @PrivilegeDescription("Privilege to transfer balance")
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.profile}, 'PREPAYBALANCE.BALANCE.LIST')")
    public ResponseEntity<List<TransferBalance>> listTransferBalance(String fields, Integer offset, Integer limit) {
        return ResponseEntity.ok().build();
    }

    @Timed
    @Override
    @LogicExtensionPoint(value = "RetrieveTransferBalance")
    @PrivilegeDescription("Privilege to transfer balance")
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.profile}, 'PREPAYBALANCE.BALANCE.RETRIEVE')")
    public ResponseEntity<TransferBalance> retrieveTransferBalance(String id, String fields) {
        return ResponseEntity.ok().build();
    }
}
