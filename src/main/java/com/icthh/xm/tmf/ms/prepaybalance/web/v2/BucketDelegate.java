package com.icthh.xm.tmf.ms.prepaybalance.web.v2;

import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.prepaybalance.lep.keyresolver.ProfileKeyResolver;
import com.icthh.xm.tmf.ms.prepaybalance.web.v2.api.BucketApiDelegate;
import com.icthh.xm.tmf.ms.prepaybalance.web.v2.api.model.BucketBalance;
import io.micrometer.core.annotation.Timed;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@LepService(group = "service")
public class BucketDelegate implements BucketApiDelegate {

    @Timed
    @Override
    @LogicExtensionPoint(value = "RetrieveBuckets", resolver = ProfileKeyResolver.class)
    @PrivilegeDescription("Privilege to retrieve buckets")
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.profile}, 'PREPAYBALANCE.BUCKETS.RETRIEVE')")
    public ResponseEntity<List<BucketBalance>> retrieveBuckets(String productId) {
        return ResponseEntity.ok().build();
    }
}
