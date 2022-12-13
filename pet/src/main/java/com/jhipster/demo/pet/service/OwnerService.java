package com.jhipster.demo.pet.service;

import com.jhipster.demo.pet.domain.Owner;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Owner}.
 */
public interface OwnerService {
    /**
     * Save a owner.
     *
     * @param owner the entity to save.
     * @return the persisted entity.
     */
    Owner save(Owner owner);

    /**
     * Updates a owner.
     *
     * @param owner the entity to update.
     * @return the persisted entity.
     */
    Owner update(Owner owner);

    /**
     * Partially updates a owner.
     *
     * @param owner the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Owner> partialUpdate(Owner owner);

    /**
     * Get all the owners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Owner> findAll(Pageable pageable);

    /**
     * Get the "id" owner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Owner> findOne(Long id);

    /**
     * Delete the "id" owner.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
