package cz.cvut.kbss.jopa.example09.persistence.criteria;

import cz.cvut.kbss.jopa.example09.model.Developer;
import cz.cvut.kbss.jopa.example09.model.Game;
import cz.cvut.kbss.jopa.example09.model.Game_;
import cz.cvut.kbss.jopa.example09.persistence.DeveloperRepository;
import cz.cvut.kbss.jopa.example09.persistence.GameRepository;
import cz.cvut.kbss.jopa.example09.persistence.QueryMechanism;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.query.criteria.CriteriaBuilder;
import cz.cvut.kbss.jopa.model.query.criteria.CriteriaQuery;
import cz.cvut.kbss.jopa.model.query.criteria.Root;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class CriteriaGameRepository implements GameRepository {

    private final EntityManager em;

    public CriteriaGameRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Game> findAll() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Game> query = cb.createQuery(Game.class);
        final Root<Game> root = query.from(Game.class);
        query.select(query.from(Game.class)).orderBy(cb.desc(root.getAttr("releaseDate")));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Game> findAll(LocalDate from, LocalDate to) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Game> query = cb.createQuery(Game.class);
        final Root<Game> root = query.from(Game.class);
        query.select(root)
             .where(cb.greaterThan(root.getAttr(Game_.releaseDate), from),
                    cb.lessThan(root.getAttr(Game_.releaseDate), to))
             .orderBy(cb.desc(root.getAttr(Game_.releaseDate)));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Game> findAll(Developer developer) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Game> query = cb.createQuery(Game.class);
        final Root<Game> root = query.from(Game.class);
        query.select(root)
             .where(cb.equal(root.getAttr(Game_.developer), developer))
             .orderBy(cb.desc(root.getAttr(Game_.releaseDate)));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Game> findAllBySmallDevelopers() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Game> query = cb.createQuery(Game.class);
        final Root<Game> root = query.from(Game.class);
        query.select(root)
             .where(cb.lessThan(root.getAttr("developer").getAttr("employeeCount"),
                                DeveloperRepository.SMALL_DEVELOPER_SIZE))
             .orderBy(cb.desc(root.getAttr("releaseDate")));
        return em.createQuery(query).getResultList();
    }

    @Override
    public QueryMechanism getQueryMechanism() {
        return QueryMechanism.CRITERIA;
    }
}
