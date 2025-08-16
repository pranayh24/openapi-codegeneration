package org.openapi.codegen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapi.codegen.document.OpenApiSpec;
import org.openapi.codegen.exception.ValidationException;
import org.openapi.codegen.repository.OpenApiSpecRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenApiSpecService {

    private final OpenApiSpecRepository openApiSpecRepository;

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    public OpenApiSpec uploadAndValidateSpec(MultipartFile file, String userId, String name, String description) {
        log.info("Uploading OpenAPI specification for user: {} with name: {}", userId, name);

        try {
            String content = new String(file.getBytes());
            Map<String, Object> specContent = parseSpecContent(content, file.getOriginalFilename());

            SwaggerParseResult parseResult = new OpenAPIParser().readContents(content, null, null);
            OpenAPI openAPI = parseResult.getOpenAPI();

            OpenApiSpec spec = new OpenApiSpec(name, specContent, userId);
            spec.setDescription(description);
            spec.setOriginalFilename(file.getOriginalFilename());
            spec.setFileSize(file.getSize());
            spec.setContentType(file.getContentType());
            spec.setCreatedBy(userId);
            spec.setUpdatedBy(userId);

            if (openAPI != null) {
                extractDetailedMetadata(spec, openAPI);
                spec.setValid(true);
                log.info("OpenAPI specification validation successfully");
            } else {
                spec.setValid(false);
                spec.setValidationErrors(String.join(", ", parseResult.getMessages()));
                log.warn("OpenAPI specification validation failed", parseResult.getMessages());
            }

            OpenApiSpec savedSpec = openApiSpecRepository.save(spec);
            log.info("OpenAPI specification saved with id: {}", savedSpec.getId());

            return savedSpec;
        } catch (Exception e) {
            log.error("Error uploading OpenAPI specification", e);
            throw new ValidationException("Failed to upload OpenAPI specification: " + e.getMessage(), e);
        }
    }

    private Map<String, Object> parseSpecContent(String content, String originalFilename) {
        return null;
    }

    private void extractDetailedMetadata(OpenApiSpec spec, OpenAPI openAPI) {

    }
}
