package com.jhipster.demo.pet.service.impl;

import com.jhipster.demo.pet.domain.Visit;
import com.jhipster.demo.pet.repository.VisitRepository;
import com.jhipster.demo.pet.service.VisitService;
import com.jhipster.demo.pet.service.dto.VisitDTO;
import com.jhipster.demo.pet.service.mapper.VisitMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Visit}.
 */
@Service
public class VisitServiceImpl implements VisitService {

    private final Logger log = LoggerFactory.getLogger(VisitServiceImpl.class);

    private final VisitRepository visitRepository;

    private final VisitMapper visitMapper;

    public VisitServiceImpl(VisitRepository visitRepository, VisitMapper visitMapper) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
    }

    @Override
    public VisitDTO save(VisitDTO visitDTO) {
        log.debug("Request to save Visit : {}", visitDTO);
        Visit visit = visitMapper.toEntity(visitDTO);
        visit = visitRepository.save(visit);
        return visitMapper.toDto(visit);
    }

    @Override
    public VisitDTO update(VisitDTO visitDTO) {
        log.debug("Request to update Visit : {}", visitDTO);
        Visit visit = visitMapper.toEntity(visitDTO);
        visit = visitRepository.save(visit);
        return visitMapper.toDto(visit);
    }

    @Override
    public Optional<VisitDTO> partialUpdate(VisitDTO visitDTO) {
        log.debug("Request to partially update Visit : {}", visitDTO);

        return visitRepository
            .findById(visitDTO.getId())
            .map(existingVisit -> {
                visitMapper.partialUpdate(existingVisit, visitDTO);

                return existingVisit;
            })
            .map(visitRepository::save)
            .map(visitMapper::toDto);
    }

    @Override
    public Page<VisitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Visits");
        return visitRepository.findAll(pageable).map(visitMapper::toDto);
    }

    public Page<VisitDTO> findAllWithEagerRelationships(Pageable pageable) {
        return visitRepository.findAllWithEagerRelationships(pageable).map(visitMapper::toDto);
    }

    @Override
    public Optional<VisitDTO> findOne(String id) {
        log.debug("Request to get Visit : {}", id);
        return visitRepository.findOneWithEagerRelationships(id).map(visitMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Visit : {}", id);
        visitRepository.deleteById(id);
    }
}
