package com.icthh.xm.tmf.ms.prepaybalance.web.v4;

import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.prepaybalance.lep.keyresolver.ProfileKeyResolver;
import com.icthh.xm.tmf.ms.prepaybalance.web.v4.api.model.Bucket;
import com.icthh.xm.tmf.ms.prepaybalance.web.v4.api.BucketApiDelegate;
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
    @LogicExtensionPoint(value = "ListBuckets", resolver = ProfileKeyResolver.class)
    @PrivilegeDescription("Privilege to list buckets")
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.profile}, 'LIST.BUCKETS')")
    public ResponseEntity<List<Bucket>> listBucket(String fields, Integer offset, Integer limit) {
        return ResponseEntity.ok().build();
    }
}
