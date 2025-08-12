package org.openapi.codegen.repository;

import org.openapi.codegen.document.GenerationJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GenerationJobRepository extends MongoRepository<GenerationJob, String> {

    List<GenerationJob> findByUserId(String userId);

    Page<GenerationJob> findByUserId(String userId, Pageable pageable);

    List<GenerationJob> findByUserIdAndStatus(String userId, GenerationJob.Status status);

    Optional<GenerationJob> findByUserIdAndId(String userId, String id);

    List<GenerationJob> findByOpenapiSpecId(String openapiSpecId);

    @Query("{'status': ?0}")
    List<GenerationJob> findByStatus(GenerationJob.Status status);

    @Query("{'language': ?0}")
    List<GenerationJob> findByLanguage(GenerationJob.Language language);

    @Query("{'framework': ?0}")
    List<GenerationJob> findByFramework(GenerationJob.Framework framework);

    @Query("{'createdAt': {$gte: ?0, $lte: ?1}}")
    List<GenerationJob> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("{'status': 'IN_PROGRESS', 'startedAt': {$lt: ?0}}")
    List<GenerationJob> findStuckJobs(LocalDateTime cutoffTime);

    long countByUserId(String userId);

    long countByUserIdAndStatus(String userId, GenerationJob.Status status);

    @Query(value = "{'userId': ?0}", count = true)
    long countSuccessfulJobsByUserId(String userId);

    @Query("{'actualCost': {$exists: true}}")
    List<GenerationJob> findJobsWithCost();
}
