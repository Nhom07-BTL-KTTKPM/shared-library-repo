package iuh.fit.shared.autoconfigure;

import iuh.fit.shared.web.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(GlobalExceptionHandler.class)
public class SharedWebAutoConfiguration {
}
