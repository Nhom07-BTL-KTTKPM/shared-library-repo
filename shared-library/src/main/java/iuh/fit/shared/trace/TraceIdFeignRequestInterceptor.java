package iuh.fit.shared.trace;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;

import java.util.UUID;

public class TraceIdFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String traceId = TraceIdContext.get();
        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString();
            TraceIdContext.set(traceId);
            MDC.put(TraceIdConstants.MDC_KEY, traceId);
        }

        template.header(TraceIdConstants.HEADER_NAME, traceId);
    }
}
