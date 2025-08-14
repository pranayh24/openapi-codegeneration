package org.openapi.codegen.repository;

import org.openapi.codegen.document.ApiPattern;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiPatternRepository extends MongoRepository<ApiPattern, String> {

    Optional<ApiPattern> findByPatternName(String patternName);

    List<ApiPattern> findByPatternType(String patternType);

    List<ApiPattern> findByAuthType(String authType);

    @Query("{'usageCount': {$gte: ?0}}")
    List<ApiPattern> findByUsageCountGreaterThanEqual(Long count);

    @Query("{'successRate': {$gte: ?0}}")
    List<ApiPattern> findBySuccessRateGreaterThanEqual(Double rate);

    @Query(value = "{}", sort = "{'usageCount': -1}")
    List<ApiPattern> findAllOrderByUsageCountDesc();

    @Query(value = "{}", sort = "{'successRate': -1}")
    List<ApiPattern> findAllOrderBySuccessRateDesc();

    @Query("{'endpoints': {$in: [?0]}}")
    List<ApiPattern> findByEndpointsContaining(String endpoint);

    @Query("{'httpMethods': {$in: [?0]}}")
    List<ApiPattern> findByHttpMethodsContaining(String method);
}
