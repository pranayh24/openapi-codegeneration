package org.openapi.codegen.exception;

public class GenerationExceptiom extends OpenApiCodegenException {

    public GenerationExceptiom(String message) {
        super(message, "GENERATION_ERROR");
    }

    public GenerationExceptiom(String message, Throwable cause) {
        super(message, "GENERATION_ERROR", cause);
    }
}
