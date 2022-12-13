package com.jhipster.demo.pet.service.impl;

import com.jhipster.demo.pet.domain.Visit;
import com.jhipster.demo.pet.repository.VisitRepository;
import com.jhipster.demo.pet.service.VisitService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Visit}.
 */
@Service
@Transactional
public class VisitServiceImpl implements VisitService {

    private final Logger log = LoggerFactory.getLogger(VisitServiceImpl.class);

    private final VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Visit save(Visit visit) {
        log.debug("Request to save Visit : {}", visit);
        return visitRepository.save(visit);
    }

    @Override
    public Visit update(Visit visit) {
        log.debug("Request to update Visit : {}", visit);
        return visitRepository.save(visit);
    }

    @Override
    public Optional<Visit> partialUpdate(Visit visit) {
        log.debug("Request to partially update Visit : {}", visit);

        return visitRepository
            .findById(visit.getId())
            .map(existingVisit -> {
                if (visit.getVisitDate() != null) {
                    existingVisit.setVisitDate(visit.getVisitDate());
                }
                if (visit.getPetId() != null) {
                    existingVisit.setPetId(visit.getPetId());
                }
                if (visit.getVetId() != null) {
                    existingVisit.setVetId(visit.getVetId());
                }
                if (visit.getDescription() != null) {
                    existingVisit.setDescription(visit.getDescription());
                }

                return existingVisit;
            })
            .map(visitRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Visit> findAll(Pageable pageable) {
        log.debug("Request to get all Visits");
        return visitRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Visit> findOne(Long id) {
        log.debug("Request to get Visit : {}", id);
        return visitRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Visit : {}", id);
        visitRepository.deleteById(id);
    }
}
