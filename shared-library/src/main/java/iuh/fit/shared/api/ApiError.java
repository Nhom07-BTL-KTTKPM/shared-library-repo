package iuh.fit.shared.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ApiError(
        String code,
        String detail,
        Map<String, Object> metadata,
        List<ValidationViolation> violations
) {
}
