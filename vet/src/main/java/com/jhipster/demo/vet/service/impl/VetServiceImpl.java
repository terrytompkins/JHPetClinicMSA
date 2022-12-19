package com.jhipster.demo.vet.service.impl;

import com.jhipster.demo.vet.domain.Vet;
import com.jhipster.demo.vet.repository.VetRepository;
import com.jhipster.demo.vet.service.VetService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Vet}.
 */
@Service
@Transactional
public class VetServiceImpl implements VetService {

    private final Logger log = LoggerFactory.getLogger(VetServiceImpl.class);

    private final VetRepository vetRepository;

    public VetServiceImpl(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public Vet save(Vet vet) {
        log.debug("Request to save Vet : {}", vet);
        return vetRepository.save(vet);
    }

    @Override
    public Vet update(Vet vet) {
        log.debug("Request to update Vet : {}", vet);
        return vetRepository.save(vet);
    }

    @Override
    public Optional<Vet> partialUpdate(Vet vet) {
        log.debug("Request to partially update Vet : {}", vet);

        return vetRepository
            .findById(vet.getId())
            .map(existingVet -> {
                if (vet.getFirstName() != null) {
                    existingVet.setFirstName(vet.getFirstName());
                }
                if (vet.getLastName() != null) {
                    existingVet.setLastName(vet.getLastName());
                }

                return existingVet;
            })
            .map(vetRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Vet> findAll(Pageable pageable) {
        log.debug("Request to get all Vets");
        return vetRepository.findAll(pageable);
    }

    public Page<Vet> findAllWithEagerRelationships(Pageable pageable) {
        return vetRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vet> findOne(Long id) {
        log.debug("Request to get Vet : {}", id);
        return vetRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vet : {}", id);
        vetRepository.deleteById(id);
    }
}
