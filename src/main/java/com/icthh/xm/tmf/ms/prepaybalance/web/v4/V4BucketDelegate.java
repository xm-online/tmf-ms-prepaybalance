package com.icthh.xm.tmf.ms.prepaybalance.web.v4;

import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.logging.LoggingAspectConfig;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.tmf.ms.prepaybalance.lep.keyresolver.ProfileKeyResolver;
import com.icthh.xm.tmf.ms.prepaybalance.web.v4.api.model.Bucket;
import com.icthh.xm.tmf.ms.prepaybalance.web.v4.api.V4BucketApiDelegate;
import io.micrometer.core.annotation.Timed;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@LepService(group = "service")
public class V4BucketDelegate implements V4BucketApiDelegate {

    @Timed
    @Override
    @LoggingAspectConfig(resultCollectionAware = false)
    @LogicExtensionPoint(value = "ListBucket", resolver = ProfileKeyResolver.class)
    @PrivilegeDescription("Privilege to list bucket")
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.profile}, 'PREPAYBALANCE.BUCKET.LIST')")
    public ResponseEntity<List<Bucket>> listBucket(String fields, Integer offset, Integer limit) {
        return ResponseEntity.ok().build();
    }

    @Timed
    @Override
    @LoggingAspectConfig(resultCollectionAware = false)
    @LogicExtensionPoint(value = "RetrieveBucket", resolver = ProfileKeyResolver.class)
    @PrivilegeDescription("Privilege to retrieve bucket")
    @PreAuthorize("hasPermission({'profile': @headerRequestExtractor.profile}, 'PREPAYBALANCE.BUCKET.RETRIEVE')")
    public ResponseEntity<Bucket> retrieveBucket(String id, String fields) {
        return ResponseEntity.ok().build();
    }
}
