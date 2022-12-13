package com.jhipster.demo.pet.repository;

import com.jhipster.demo.pet.domain.Visit;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Visit entity.
 */
@Repository
public interface VisitRepository extends MongoRepository<Visit, String> {
    @Query("{}")
    Page<Visit> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Visit> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Visit> findOneWithEagerRelationships(String id);
}
