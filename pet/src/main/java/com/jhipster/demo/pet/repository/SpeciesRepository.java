package com.jhipster.demo.pet.repository;

import com.jhipster.demo.pet.domain.Species;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Species entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpeciesRepository extends MongoRepository<Species, String> {}
