package cz.cvut.kbss.jopa.example09.persistence.criteria;

import cz.cvut.kbss.jopa.example09.model.Developer;
import cz.cvut.kbss.jopa.example09.model.Developer_;
import cz.cvut.kbss.jopa.example09.persistence.DeveloperRepository;
import cz.cvut.kbss.jopa.example09.persistence.QueryMechanism;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.query.criteria.CriteriaBuilder;
import cz.cvut.kbss.jopa.model.query.criteria.CriteriaQuery;
import cz.cvut.kbss.jopa.model.query.criteria.Root;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Repository
public class CriteriaDeveloperRepository implements DeveloperRepository {

    private final EntityManager em;

    public CriteriaDeveloperRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Developer> findAll() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Developer> query = cb.createQuery(Developer.class);
        final Root<Developer> root = query.from(Developer.class);
        query.select(query.from(Developer.class)).orderBy(cb.asc(root.getAttr(Developer_.name)));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Developer> findByName(String name) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Developer> query = cb.createQuery(Developer.class);
        final Root<Developer> root = query.from(Developer.class);
        query.select(root)
             .where(cb.like(root.getAttr(Developer_.name), name))
             .orderBy(cb.asc(root.getAttr(Developer_.name)));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Developer> findSmallDevelopers() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Developer> query = cb.createQuery(Developer.class);
        final Root<Developer> root = query.from(Developer.class);
        query.select(root)
             .where(cb.lessThan(root.getAttr(Developer_.employeeCount), DeveloperRepository.SMALL_DEVELOPER_SIZE))
             .orderBy(cb.asc(root.getAttr(Developer_.name)));
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Developer> findById(String localName) {
        return Optional.ofNullable(em.find(Developer.class, URI.create(NAMESPACE + localName)));
    }

    @Override
    public QueryMechanism getQueryMechanism() {
        return QueryMechanism.CRITERIA;
    }
}
