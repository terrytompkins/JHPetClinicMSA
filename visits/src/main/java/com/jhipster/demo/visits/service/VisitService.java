package com.jhipster.demo.visits.service;

import com.jhipster.demo.visits.domain.Visit;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link Visit}.
 */
public interface VisitService {
    /**
     * Save a visit.
     *
     * @param visit the entity to save.
     * @return the persisted entity.
     */
    Mono<Visit> save(Visit visit);

    /**
     * Updates a visit.
     *
     * @param visit the entity to update.
     * @return the persisted entity.
     */
    Mono<Visit> update(Visit visit);

    /**
     * Partially updates a visit.
     *
     * @param visit the entity to update partially.
     * @return the persisted entity.
     */
    Mono<Visit> partialUpdate(Visit visit);

    /**
     * Get all the visits.
     *
     * @return the list of entities.
     */
    Flux<Visit> findAll();

    /**
     * Returns the number of visits available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" visit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<Visit> findOne(Long id);

    /**
     * Delete the "id" visit.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
