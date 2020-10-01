package com.icthh.xm.tmf.ms.prepaybalance.web;

import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.prepaybalance.lep.keyresolver.ProfileKeyResolver;
import com.icthh.xm.tmf.ms.prepaybalance.web.api.BucketApiDelegate;
import com.icthh.xm.tmf.ms.prepaybalance.web.api.model.BucketBalance;
import io.micrometer.core.annotation.Timed;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@LepService(group = "service", name = "default")
public class BucketDelegate implements BucketApiDelegate {

    @Timed
    @Override
    @LogicExtensionPoint(value = "RetrieveBuckets", resolver = ProfileKeyResolver.class)
    @PrivilegeDescription("Privilege to retrieve buckets")
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.profile}, 'RETRIEVE.BUCKETS')")
    public ResponseEntity<List<BucketBalance>> retrieveBuckets(String productId) {
        return ResponseEntity.ok().build();
    }
}
