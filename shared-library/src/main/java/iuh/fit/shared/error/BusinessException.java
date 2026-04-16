package iuh.fit.shared.error;

import java.util.Collections;
import java.util.Map;

public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Map<String, Object> metadata;

    public BusinessException(ErrorCode errorCode) {
        this(errorCode, errorCode.defaultMessage(), Collections.emptyMap());
    }

    public BusinessException(ErrorCode errorCode, String message) {
        this(errorCode, message, Collections.emptyMap());
    }

    public BusinessException(ErrorCode errorCode, String message, Map<String, Object> metadata) {
        super(message);
        this.errorCode = errorCode;
        this.metadata = metadata == null ? Collections.emptyMap() : Map.copyOf(metadata);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }
}
