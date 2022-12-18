package com.jhipster.demo.visits.service.impl;

import com.jhipster.demo.visits.domain.Visit;
import com.jhipster.demo.visits.repository.VisitRepository;
import com.jhipster.demo.visits.service.VisitService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<Visit> save(Visit visit) {
        log.debug("Request to save Visit : {}", visit);
        return visitRepository.save(visit);
    }

    @Override
    public Mono<Visit> update(Visit visit) {
        log.debug("Request to update Visit : {}", visit);
        return visitRepository.save(visit);
    }

    @Override
    public Mono<Visit> partialUpdate(Visit visit) {
        log.debug("Request to partially update Visit : {}", visit);

        return visitRepository
            .findById(visit.getId())
            .map(existingVisit -> {
                if (visit.getStart() != null) {
                    existingVisit.setStart(visit.getStart());
                }
                if (visit.getEnd() != null) {
                    existingVisit.setEnd(visit.getEnd());
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
            .flatMap(visitRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Visit> findAll() {
        log.debug("Request to get all Visits");
        return visitRepository.findAll();
    }

    public Mono<Long> countAll() {
        return visitRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Visit> findOne(Long id) {
        log.debug("Request to get Visit : {}", id);
        return visitRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Visit : {}", id);
        return visitRepository.deleteById(id);
    }
}
