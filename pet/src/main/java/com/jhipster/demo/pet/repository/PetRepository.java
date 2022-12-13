package com.jhipster.demo.pet.repository;

import com.jhipster.demo.pet.domain.Pet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Pet entity.
 */
@Repository
public interface PetRepository extends MongoRepository<Pet, String> {
    @Query("{}")
    Page<Pet> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Pet> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Pet> findOneWithEagerRelationships(String id);
}
