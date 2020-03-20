package cz.cvut.kbss.jopa.example09.persistence;

import cz.cvut.kbss.jopa.example09.model.Developer;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Repository
public class DeveloperRepository {

    public static final String NAMESPACE = "http://dbpedia.org/resource/";
    static final int SMALL_DEVELOPER_SIZE = 100;

    private final EntityManager em;

    @Autowired
    public DeveloperRepository(EntityManager em) {
        this.em = em;
    }

    public List<Developer> findAll() {
        return em.createQuery("SELECT DISTINCT d FROM Developer d ORDER BY d.name", Developer.class).getResultList();
    }

    public List<Developer> findByName(String name) {
        return em
                .createQuery("SELECT DISTINCT d FROM Developer d WHERE d.name LIKE :name ORDER BY d.name",
                        Developer.class)
                .setParameter("name", name).getResultList();
    }

    public List<Developer> findSmallDevelopers() {
        return em.createQuery("SELECT DISTINCT d FROM Developer d WHERE d.employeeCount < :threshold ORDER BY d.name",
                Developer.class).setParameter("threshold", SMALL_DEVELOPER_SIZE).getResultList();
    }

    public Optional<Developer> findById(String localName) {
        return Optional.ofNullable(em.find(Developer.class, URI.create(NAMESPACE + localName)));
    }
}
