package com.jhipster.demo.pet.service;

import com.jhipster.demo.pet.domain.Species;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Species}.
 */
public interface SpeciesService {
    /**
     * Save a species.
     *
     * @param species the entity to save.
     * @return the persisted entity.
     */
    Species save(Species species);

    /**
     * Updates a species.
     *
     * @param species the entity to update.
     * @return the persisted entity.
     */
    Species update(Species species);

    /**
     * Partially updates a species.
     *
     * @param species the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Species> partialUpdate(Species species);

    /**
     * Get all the species.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Species> findAll(Pageable pageable);

    /**
     * Get the "id" species.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Species> findOne(Long id);

    /**
     * Delete the "id" species.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
