package com.jhipster.demo.pet.repository;

import com.jhipster.demo.pet.domain.Owner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Owner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OwnerRepository extends MongoRepository<Owner, String> {}
