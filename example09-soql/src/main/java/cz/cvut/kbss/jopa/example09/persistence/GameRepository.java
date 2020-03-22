package cz.cvut.kbss.jopa.example09.persistence;

import cz.cvut.kbss.jopa.example09.model.Developer;
import cz.cvut.kbss.jopa.example09.model.Game;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class GameRepository {

    /**
     * Small developer has at most this amount of employees.
     */
    private static final int SMALL_DEVELOPER_THRESHOLD = 100;

    private final EntityManager em;

    @Autowired
    public GameRepository(EntityManager em) {
        this.em = em;
    }

    public List<Game> findAll() {
        return em.createQuery("SELECT g FROM Game g ORDER BY g.releaseDate DESC", Game.class).getResultList();
    }

    public List<Game> findAll(LocalDate from, LocalDate to) {
        return em.createQuery(
                "SELECT DISTINCT g FROM Game g WHERE g.releaseDate >= :from AND g.releaseDate < :to ORDER BY g.releaseDate DESC",
                Game.class)
                 .setParameter("from", from)
                 .setParameter("to", to).getResultList();
    }

    public List<Game> findAll(Developer developer) {
        return em.createQuery("SELECT DISTINCT g FROM Game g WHERE g.developer = :developer ORDER BY g.releaseDate DESC",
                Game.class).setParameter("developer", developer).getResultList();
    }

    public List<Game> findAllBySmallDevelopers() {
        return em.createQuery(
                "SELECT DISTINCT g FROM Game g WHERE g.developer.employeeCount < :maxCount ORDER BY g.releaseDate DESC",
                Game.class)
                 .setParameter("maxCount", SMALL_DEVELOPER_THRESHOLD).getResultList();
    }
}
