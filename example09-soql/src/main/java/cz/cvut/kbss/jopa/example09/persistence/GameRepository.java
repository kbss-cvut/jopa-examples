package cz.cvut.kbss.jopa.example09.persistence;

import cz.cvut.kbss.jopa.example09.model.Game;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameRepository {

    private final EntityManager em;

    @Autowired
    public GameRepository(EntityManager em) {
        this.em = em;
    }

    public List<Game> findAll() {
        return em.createQuery("SELECT g FROM Game g ORDER BY g.releaseDate DESC", Game.class).getResultList();
    }
}
