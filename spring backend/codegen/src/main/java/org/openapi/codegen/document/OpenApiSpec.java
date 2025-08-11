package org.openapi.codegen.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Document(collation = "openapi_specs")
@Getter
@Setter
public class OpenApiSpec extends BaseDocument {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Field("spec_content")
    private Map<String, Object> specContent;

    @Field("original_filename")
    private String originalFilename;

    @Field("content_type")
    private String contentType;

    @Indexed
    @Field("user_id")
    private String userId;

    @Field("openapi_version")
    private String openapiVersion;

    @Field("api_title")
    private String apiTitle;

    @Field("api_version")
    private String apiVersion;

    @Field("endpoints_count")
    private Integer endpointsCount;

    @Field("complexity_score")
    private Double complexityScore;

    @Field("analysis_metadata")
    private Map<String, Object> analysisMetadata;

    @Field("is_valid")
    private boolean isValid = true;

    @Field("validation_errors")
    private String validationErrors;

    public OpenApiSpec() {
        super();
    }

    public OpenApiSpec(String name, Map<String, Object> specContent, String userId) {
        this();
        this.name = name;
        this.specContent = specContent;
        this.userId = userId;
    }
}
