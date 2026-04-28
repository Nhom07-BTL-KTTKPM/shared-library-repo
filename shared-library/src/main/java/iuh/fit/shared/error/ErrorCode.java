package iuh.fit.shared.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    VALIDATION_ERROR("VALIDATION_ERROR", HttpStatus.BAD_REQUEST, "Validation failed"),
    BAD_REQUEST("BAD_REQUEST", HttpStatus.BAD_REQUEST, "Bad request"),
    UNAUTHORIZED("UNAUTHORIZED", HttpStatus.UNAUTHORIZED, "Unauthorized"),
    FORBIDDEN("FORBIDDEN", HttpStatus.FORBIDDEN, "Forbidden"),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND, "Resource not found"),
    CONFLICT("CONFLICT", HttpStatus.CONFLICT, "Conflict"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error");

    private final String code;
    private final HttpStatus httpStatus;
    private final String defaultMessage;

    ErrorCode(String code, HttpStatus httpStatus, String defaultMessage) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.defaultMessage = defaultMessage;
    }

    public String code() {
        return code;
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }

    public String defaultMessage() {
        return defaultMessage;
    }
}
