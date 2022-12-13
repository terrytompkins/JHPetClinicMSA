package com.jhipster.demo.pet.repository;

import com.jhipster.demo.pet.domain.Species;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Species entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {}
