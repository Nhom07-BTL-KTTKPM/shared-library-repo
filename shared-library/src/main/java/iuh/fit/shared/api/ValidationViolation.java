package iuh.fit.shared.api;

public record ValidationViolation(
        String field,
        String message,
        String rejectedValue
) {
}
