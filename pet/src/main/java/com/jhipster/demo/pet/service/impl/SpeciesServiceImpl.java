package com.jhipster.demo.pet.service.impl;

import com.jhipster.demo.pet.domain.Species;
import com.jhipster.demo.pet.repository.SpeciesRepository;
import com.jhipster.demo.pet.service.SpeciesService;
import com.jhipster.demo.pet.service.dto.SpeciesDTO;
import com.jhipster.demo.pet.service.mapper.SpeciesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Species}.
 */
@Service
public class SpeciesServiceImpl implements SpeciesService {

    private final Logger log = LoggerFactory.getLogger(SpeciesServiceImpl.class);

    private final SpeciesRepository speciesRepository;

    private final SpeciesMapper speciesMapper;

    public SpeciesServiceImpl(SpeciesRepository speciesRepository, SpeciesMapper speciesMapper) {
        this.speciesRepository = speciesRepository;
        this.speciesMapper = speciesMapper;
    }

    @Override
    public SpeciesDTO save(SpeciesDTO speciesDTO) {
        log.debug("Request to save Species : {}", speciesDTO);
        Species species = speciesMapper.toEntity(speciesDTO);
        species = speciesRepository.save(species);
        return speciesMapper.toDto(species);
    }

    @Override
    public SpeciesDTO update(SpeciesDTO speciesDTO) {
        log.debug("Request to update Species : {}", speciesDTO);
        Species species = speciesMapper.toEntity(speciesDTO);
        species = speciesRepository.save(species);
        return speciesMapper.toDto(species);
    }

    @Override
    public Optional<SpeciesDTO> partialUpdate(SpeciesDTO speciesDTO) {
        log.debug("Request to partially update Species : {}", speciesDTO);

        return speciesRepository
            .findById(speciesDTO.getId())
            .map(existingSpecies -> {
                speciesMapper.partialUpdate(existingSpecies, speciesDTO);

                return existingSpecies;
            })
            .map(speciesRepository::save)
            .map(speciesMapper::toDto);
    }

    @Override
    public Page<SpeciesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Species");
        return speciesRepository.findAll(pageable).map(speciesMapper::toDto);
    }

    @Override
    public Optional<SpeciesDTO> findOne(String id) {
        log.debug("Request to get Species : {}", id);
        return speciesRepository.findById(id).map(speciesMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Species : {}", id);
        speciesRepository.deleteById(id);
    }
}
