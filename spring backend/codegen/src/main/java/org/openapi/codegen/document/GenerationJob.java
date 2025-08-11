package org.openapi.codegen.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "generation_jobs")
@Getter
@Setter
public class GenerationJob extends BaseDocument {

    public enum Status {
        PENDING, IN_PROGRESS, COMPLETED, FAILED, CANCELLED
    }

    public enum Language {
        JAVA, PYTHON, JAVASCRIPT, TYPESCRIPT, CSHARP, GO
    }

    public enum Framework {
        SPRING_BOOT, FLASK, EXPRESS, ASP_NET, GIN
    }

    @NotBlank
    @Indexed
    @Field("user_id")
    private String userId;

    @NotBlank
    @Indexed
    @Field("openapi_spec_id")
    private String openapiSpecId;

    @NotNull
    private Language language;

    private Framework framework;

    @Field("download_url")
    private String generatedCode;

    @Field("download_url")
    private String downloadUrl;

    @Field("error_message")
    private String errorMessage;

    @Field("started_at")
    private LocalDateTime startedAt;

    @Field("completed_at")
    private LocalDateTime completedAt;

    @Field("processing_time_ms")
    private Long processingTimeMs;

    @Field("estimated_cost")
    private Double estimatedCost;

    @Field("actual_cost")
    private Double actualCost;

    @Field("complexity_score")
    private Integer tokensUsed;

    private Map<String, Object> mlMetadata;

    public GenerationJob() {
        super();
    }

    public GenerationJob(String userId, String openapiSpecId, Language language) {
        this();
        this.userId = userId;
        this.openapiSpecId = openapiSpecId;
        this.language = language;

    }
}
