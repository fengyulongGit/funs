package com.autils.api.exception;

public class ApiException extends RuntimeException {
    public static final int STATUS_CODE_NO_NETWORK = -8888;

    private int resultCode;

    public ApiException(int resultCode, String desc) {
        this(desc);
        this.resultCode = resultCode;
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public int getResultCode() {
        return resultCode;
    }

}

