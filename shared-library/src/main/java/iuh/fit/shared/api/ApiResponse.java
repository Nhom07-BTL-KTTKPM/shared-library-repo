package iuh.fit.shared.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        Instant timestamp,
        boolean success,
        String message,
        T data,
        ApiError error,
        String traceId
) {

    public static <T> ApiResponse<T> success(T data, String message, String traceId) {
        return new ApiResponse<>(Instant.now(), true, message, data, null, traceId);
    }

    public static <T> ApiResponse<T> failure(String message, ApiError error, String traceId) {
        return new ApiResponse<>(Instant.now(), false, message, null, error, traceId);
    }
}
