package iuh.fit.shared.autoconfigure;

import feign.RequestInterceptor;
import iuh.fit.shared.trace.TraceIdFeignRequestInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass(RequestInterceptor.class)
public class SharedFeignTraceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "traceIdFeignRequestInterceptor")
    public RequestInterceptor traceIdFeignRequestInterceptor() {
        return new TraceIdFeignRequestInterceptor();
    }
}
