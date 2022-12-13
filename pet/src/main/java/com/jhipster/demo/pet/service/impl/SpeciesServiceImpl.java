package com.jhipster.demo.pet.service.impl;

import com.jhipster.demo.pet.domain.Species;
import com.jhipster.demo.pet.repository.SpeciesRepository;
import com.jhipster.demo.pet.service.SpeciesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Species}.
 */
@Service
@Transactional
public class SpeciesServiceImpl implements SpeciesService {

    private final Logger log = LoggerFactory.getLogger(SpeciesServiceImpl.class);

    private final SpeciesRepository speciesRepository;

    public SpeciesServiceImpl(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public Species save(Species species) {
        log.debug("Request to save Species : {}", species);
        return speciesRepository.save(species);
    }

    @Override
    public Species update(Species species) {
        log.debug("Request to update Species : {}", species);
        return speciesRepository.save(species);
    }

    @Override
    public Optional<Species> partialUpdate(Species species) {
        log.debug("Request to partially update Species : {}", species);

        return speciesRepository
            .findById(species.getId())
            .map(existingSpecies -> {
                if (species.getName() != null) {
                    existingSpecies.setName(species.getName());
                }

                return existingSpecies;
            })
            .map(speciesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Species> findAll(Pageable pageable) {
        log.debug("Request to get all Species");
        return speciesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Species> findOne(Long id) {
        log.debug("Request to get Species : {}", id);
        return speciesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Species : {}", id);
        speciesRepository.deleteById(id);
    }
}
