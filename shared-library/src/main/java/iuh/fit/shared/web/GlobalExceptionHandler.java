package iuh.fit.shared.web;

import iuh.fit.shared.api.ApiError;
import iuh.fit.shared.api.ApiResponse;
import iuh.fit.shared.api.ValidationViolation;
import iuh.fit.shared.error.BusinessException;
import iuh.fit.shared.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String TRACE_ID_HEADER = "X-Trace-Id";

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        ErrorCode errorCode = ex.getErrorCode();
        ApiError error = new ApiError(errorCode.code(), ex.getMessage(), ex.getMetadata(), List.of());

        return ResponseEntity.status(errorCode.httpStatus())
                .body(ApiResponse.failure(ex.getMessage(), error, resolveTraceId(request)));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, ConstraintViolationException.class})
    public ResponseEntity<ApiResponse<Void>> handleValidationException(Exception ex, HttpServletRequest request) {
        List<ValidationViolation> violations;
        if (ex instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            violations = mapFieldErrors(methodArgumentNotValidException.getBindingResult().getFieldErrors());
        } else if (ex instanceof BindException bindException) {
            violations = mapFieldErrors(bindException.getBindingResult().getFieldErrors());
        } else if (ex instanceof ConstraintViolationException constraintViolationException) {
            violations = constraintViolationException.getConstraintViolations()
                    .stream()
                    .map(violation -> new ValidationViolation(
                            violation.getPropertyPath().toString(),
                            violation.getMessage(),
                            valueToString(violation.getInvalidValue())
                    ))
                    .toList();
        } else {
            violations = List.of();
        }

        ApiError error = new ApiError(
                ErrorCode.VALIDATION_ERROR.code(),
                ErrorCode.VALIDATION_ERROR.defaultMessage(),
                Collections.emptyMap(),
                violations
        );

        return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.httpStatus())
                .body(ApiResponse.failure(ErrorCode.VALIDATION_ERROR.defaultMessage(), error, resolveTraceId(request)));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiResponse<Void>> handleBadRequestException(Exception ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                ErrorCode.BAD_REQUEST.code(),
                ex.getMessage(),
                Map.of(),
                List.of()
        );

        return ResponseEntity.status(ErrorCode.BAD_REQUEST.httpStatus())
                .body(ApiResponse.failure(ErrorCode.BAD_REQUEST.defaultMessage(), error, resolveTraceId(request)));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleMalformedJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String detail = "Malformed JSON request";
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        if (mostSpecificCause != null && mostSpecificCause.getMessage() != null && !mostSpecificCause.getMessage().isBlank()) {
            detail = mostSpecificCause.getMessage();
        }

        ApiError error = new ApiError(
                ErrorCode.BAD_REQUEST.code(),
                detail,
                Map.of("reason", "MALFORMED_JSON"),
                List.of()
        );

        return ResponseEntity.status(ErrorCode.BAD_REQUEST.httpStatus())
                .body(ApiResponse.failure(ErrorCode.BAD_REQUEST.defaultMessage(), error, resolveTraceId(request)));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                ErrorCode.UNAUTHORIZED.code(),
                ex.getMessage(),
                Map.of(),
                List.of()
        );

        return ResponseEntity.status(ErrorCode.UNAUTHORIZED.httpStatus())
                .body(ApiResponse.failure(ErrorCode.UNAUTHORIZED.defaultMessage(), error, resolveTraceId(request)));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                ErrorCode.FORBIDDEN.code(),
                ex.getMessage(),
                Map.of(),
                List.of()
        );

        return ResponseEntity.status(ErrorCode.FORBIDDEN.httpStatus())
                .body(ApiResponse.failure(ErrorCode.FORBIDDEN.defaultMessage(), error, resolveTraceId(request)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpectedException(Exception ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                ErrorCode.INTERNAL_SERVER_ERROR.code(),
                ex.getMessage(),
                Map.of(),
                List.of()
        );

        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.httpStatus())
                .body(ApiResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.defaultMessage(), error, resolveTraceId(request)));
    }

    private static List<ValidationViolation> mapFieldErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(error -> new ValidationViolation(error.getField(), error.getDefaultMessage(), valueToString(error.getRejectedValue())))
                .toList();
    }

    private static String valueToString(Object value) {
        return value == null ? null : String.valueOf(value);
    }

    private static String resolveTraceId(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String traceId = request.getHeader(TRACE_ID_HEADER);
        return (traceId == null || traceId.isBlank()) ? null : traceId;
    }
}
