package com.client.exception;

/**
 *
 * @author leonard
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = -1597642890482393945L;

    private final ErrorCode errorCode;

    public SystemException(String message) {
        this(message, ErrorCode.UNEXPECTED);
    }

    public SystemException(String message, ErrorCode code){
        super(message);
        errorCode = code;
    }

    public SystemException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }


    public SystemException(Throwable throwable, ErrorCode errorCode) {
        super(throwable);
        this.errorCode = errorCode;
    }

    public SystemException(Throwable throwable){
        this(throwable, ErrorCode.UNEXPECTED);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
