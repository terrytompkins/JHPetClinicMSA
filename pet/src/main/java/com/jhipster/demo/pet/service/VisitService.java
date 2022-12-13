package com.jhipster.demo.pet.service;

import com.jhipster.demo.pet.service.dto.VisitDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jhipster.demo.pet.domain.Visit}.
 */
public interface VisitService {
    /**
     * Save a visit.
     *
     * @param visitDTO the entity to save.
     * @return the persisted entity.
     */
    VisitDTO save(VisitDTO visitDTO);

    /**
     * Updates a visit.
     *
     * @param visitDTO the entity to update.
     * @return the persisted entity.
     */
    VisitDTO update(VisitDTO visitDTO);

    /**
     * Partially updates a visit.
     *
     * @param visitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VisitDTO> partialUpdate(VisitDTO visitDTO);

    /**
     * Get all the visits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VisitDTO> findAll(Pageable pageable);

    /**
     * Get all the visits with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VisitDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" visit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VisitDTO> findOne(String id);

    /**
     * Delete the "id" visit.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
