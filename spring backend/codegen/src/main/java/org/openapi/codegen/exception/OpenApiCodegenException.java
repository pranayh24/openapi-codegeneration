package org.openapi.codegen.exception;

public class OpenApiCodegenException extends RuntimeException {

    private final String errorCode;

    public OpenApiCodegenException(String message) {
        super(message);
        this.errorCode = "GENERAL_ERROR";
    }

    public OpenApiCodegenException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public OpenApiCodegenException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "GENERAL_ERROR";
    }

    public OpenApiCodegenException(String message,String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
