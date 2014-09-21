package api.exceptions;

import java.util.HashMap;
import java.util.Map;

public enum VkErrorCode {
    AUTH_FAILED(5),
    UNKNOWN(0);

    private static final Map<Integer, VkErrorCode> errorCodes = new HashMap<>();

    private VkErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    static {
        for (VkErrorCode error : values())
            errorCodes.put(error.getCode(), error);
    }

    private int errorCode;

    public int getCode() {
        return errorCode;
    }

    public static VkErrorCode getError(int errorCode) {
        return (errorCodes.containsKey(errorCode)) ? errorCodes.get(errorCode) : UNKNOWN;
    }
}