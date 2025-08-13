package org.openapi.codegen.exception;

public class ResourceNotFoundException extends OpenApiCodegenException {

    public ResourceNotFoundException(String resourceType, String resourceId) {
        super(String.format("%s with id '%s' not found", resourceType, resourceId), "RESOURCE_NOT_FOUND");
    }
    public ResourceNotFoundException(String message) {
        super(message,"RESOURCE_NOT_FOUND");
    }
}
