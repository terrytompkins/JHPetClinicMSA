package com.jhipster.demo.vet.repository;

import com.jhipster.demo.vet.domain.Vet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class VetRepositoryWithBagRelationshipsImpl implements VetRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Vet> fetchBagRelationships(Optional<Vet> vet) {
        return vet.map(this::fetchSpecialties);
    }

    @Override
    public Page<Vet> fetchBagRelationships(Page<Vet> vets) {
        return new PageImpl<>(fetchBagRelationships(vets.getContent()), vets.getPageable(), vets.getTotalElements());
    }

    @Override
    public List<Vet> fetchBagRelationships(List<Vet> vets) {
        return Optional.of(vets).map(this::fetchSpecialties).orElse(Collections.emptyList());
    }

    Vet fetchSpecialties(Vet result) {
        return entityManager
            .createQuery("select vet from Vet vet left join fetch vet.specialties where vet is :vet", Vet.class)
            .setParameter("vet", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Vet> fetchSpecialties(List<Vet> vets) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, vets.size()).forEach(index -> order.put(vets.get(index).getId(), index));
        List<Vet> result = entityManager
            .createQuery("select distinct vet from Vet vet left join fetch vet.specialties where vet in :vets", Vet.class)
            .setParameter("vets", vets)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
