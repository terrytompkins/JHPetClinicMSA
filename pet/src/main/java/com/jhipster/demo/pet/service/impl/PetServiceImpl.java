package com.jhipster.demo.pet.service.impl;

import com.jhipster.demo.pet.domain.Pet;
import com.jhipster.demo.pet.repository.PetRepository;
import com.jhipster.demo.pet.service.PetService;
import com.jhipster.demo.pet.service.dto.PetDTO;
import com.jhipster.demo.pet.service.mapper.PetMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Pet}.
 */
@Service
public class PetServiceImpl implements PetService {

    private final Logger log = LoggerFactory.getLogger(PetServiceImpl.class);

    private final PetRepository petRepository;

    private final PetMapper petMapper;

    public PetServiceImpl(PetRepository petRepository, PetMapper petMapper) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
    }

    @Override
    public PetDTO save(PetDTO petDTO) {
        log.debug("Request to save Pet : {}", petDTO);
        Pet pet = petMapper.toEntity(petDTO);
        pet = petRepository.save(pet);
        return petMapper.toDto(pet);
    }

    @Override
    public PetDTO update(PetDTO petDTO) {
        log.debug("Request to update Pet : {}", petDTO);
        Pet pet = petMapper.toEntity(petDTO);
        pet = petRepository.save(pet);
        return petMapper.toDto(pet);
    }

    @Override
    public Optional<PetDTO> partialUpdate(PetDTO petDTO) {
        log.debug("Request to partially update Pet : {}", petDTO);

        return petRepository
            .findById(petDTO.getId())
            .map(existingPet -> {
                petMapper.partialUpdate(existingPet, petDTO);

                return existingPet;
            })
            .map(petRepository::save)
            .map(petMapper::toDto);
    }

    @Override
    public Page<PetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pets");
        return petRepository.findAll(pageable).map(petMapper::toDto);
    }

    public Page<PetDTO> findAllWithEagerRelationships(Pageable pageable) {
        return petRepository.findAllWithEagerRelationships(pageable).map(petMapper::toDto);
    }

    @Override
    public Optional<PetDTO> findOne(String id) {
        log.debug("Request to get Pet : {}", id);
        return petRepository.findOneWithEagerRelationships(id).map(petMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Pet : {}", id);
        petRepository.deleteById(id);
    }
}
