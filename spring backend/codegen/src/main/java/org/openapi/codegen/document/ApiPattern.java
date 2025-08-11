package org.openapi.codegen.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;
import java.util.Map;

@Document(collection = "api_patterns")
@Getter
@Setter
public class ApiPattern extends BaseDocument {

    @Indexed
    @Field("pattern_name")
    private String patternName;

    @Field("pattern_type")
    private String patternType;

    @Field("auth_type")
    private String authType;

    @Field("endpoints")
    private List<String> endpoints;

    @Field("http_methods")
    private List<String> httpMethods;

    @Field("response_formats")
    private List<String> responseFormats;

    @Field("complexity_indicators")
    private Map<String, Object> complexityIndicators;

    @Field("usage_count")
    private Long usageCount = 0L;

    @Field("success_rate")
    private Double successRate = 0.0;

    @Field("avg_generation_time")
    private Double avgGenerationTime = 0.0;

    @Field("template_mapping")
    private Map<String, String> templateMapping;

    public ApiPattern() {
        super();
    }

    public ApiPattern(String patternName, String patternType) {
        this();
        this.patternName = patternName;
        this.patternType = patternType;
    }
}
