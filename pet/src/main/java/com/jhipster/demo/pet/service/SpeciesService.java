package com.jhipster.demo.pet.service;

import com.jhipster.demo.pet.service.dto.SpeciesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jhipster.demo.pet.domain.Species}.
 */
public interface SpeciesService {
    /**
     * Save a species.
     *
     * @param speciesDTO the entity to save.
     * @return the persisted entity.
     */
    SpeciesDTO save(SpeciesDTO speciesDTO);

    /**
     * Updates a species.
     *
     * @param speciesDTO the entity to update.
     * @return the persisted entity.
     */
    SpeciesDTO update(SpeciesDTO speciesDTO);

    /**
     * Partially updates a species.
     *
     * @param speciesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SpeciesDTO> partialUpdate(SpeciesDTO speciesDTO);

    /**
     * Get all the species.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SpeciesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" species.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SpeciesDTO> findOne(String id);

    /**
     * Delete the "id" species.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
