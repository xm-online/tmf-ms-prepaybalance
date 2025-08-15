package com.icthh.xm.tmf.ms.prepaybalance.lep.keyresolver;

import com.icthh.xm.lep.api.LepKeyResolver;
import com.icthh.xm.lep.api.LepMethod;
import com.icthh.xm.tmf.ms.prepaybalance.utils.HeaderRequestExtractor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProfileKeyResolver implements LepKeyResolver {

    private final HeaderRequestExtractor headerRequestExtractor;

    @Override
    public List<String> segments(LepMethod method) {
        return List.of(headerRequestExtractor.getProfile());
    }
}
