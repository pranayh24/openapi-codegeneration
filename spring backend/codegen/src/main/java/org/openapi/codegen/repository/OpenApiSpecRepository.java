package org.openapi.codegen.repository;

import org.openapi.codegen.document.OpenApiSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpenApiSpecRepository extends MongoRepository<OpenApiSpec, String> {
    List<OpenApiSpec> findByUserId(String userId);

    Page<OpenApiSpec> findByUserId(String userId, Pageable pageable);

    List<OpenApiSpec> findByUserIdAndIsValidTrue(String userId);

    Optional<OpenApiSpec> findByUserIdAndId(String userId, String id);

    @Query("{'apiTitle': {$regex: ?0, $options: 'i'}}")
    List<OpenApiSpec> findByApiTitleContainingIgnoreCase(String title);

    @Query("{'openapiVersion': ?0}")
    List<OpenApiSpec> findByOpenapiVersion(String version);

    @Query("{'complexityScore': {$gte: ?0, $lte: ?1}}")
    List<OpenApiSpec> findByComplexityScoreBetween(Double minScore, Double maxScore);

    @Query("{'endpointsCount': {$gte: ?0}}")
    List<OpenApiSpec> findByEndpointsCountGreaterThanEqual(Integer count);

    long countByUserId(String userId);

    @Query(value = "{'userId': ?0}", count = true)
    long countByUserIdAndIsValidTrue(String userId);
}
