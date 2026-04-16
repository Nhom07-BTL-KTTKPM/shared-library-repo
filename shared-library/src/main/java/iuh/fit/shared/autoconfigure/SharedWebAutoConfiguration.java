package iuh.fit.shared.autoconfigure;

import iuh.fit.shared.trace.TraceIdFilter;
import iuh.fit.shared.web.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

@AutoConfiguration
@Import(GlobalExceptionHandler.class)
public class SharedWebAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public TraceIdFilter traceIdFilter() {
		return new TraceIdFilter();
	}

	@Bean
	@ConditionalOnMissingBean(name = "traceIdFilterRegistration")
	public FilterRegistrationBean<TraceIdFilter> traceIdFilterRegistration(TraceIdFilter traceIdFilter) {
		FilterRegistrationBean<TraceIdFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(traceIdFilter);
		registration.addUrlPatterns("/*");
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registration.setName("traceIdFilterRegistration");
		return registration;
	}
}
