package org.openapi.codegen.repository;

import org.openapi.codegen.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findByIsActiveTrue();

    @Query("{'roles': ?0}")
    List<User> findByRole(String role);

    @Query("{'generationCount': {$gte: ?0}}")
    List<User> findByGenerationCountGreaterThanEqual(Long count);

    @Query("{'totalCost': {$gte: ?0, $lte: ?1}}")
    List<User> findByTotalCostBetween(Double minCost, Double maxCost);
}