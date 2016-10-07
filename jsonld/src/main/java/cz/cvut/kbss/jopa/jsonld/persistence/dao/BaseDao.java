package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.model.util.EntityToOwlClassMapper;
import cz.cvut.kbss.jopa.jsonld.persistence.PersistenceException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * Base implementation of the generic DAO.
 */
public abstract class BaseDao<T> {

    static final Logger LOG = LoggerFactory.getLogger(BaseDao.class);

    final Class<T> type;
    final URI typeUri;

    protected BaseDao(Class<T> type) {
        this.type = type;
        this.typeUri = URI.create(EntityToOwlClassMapper.getOwlClassForEntity(type));
    }

    @Autowired
    private EntityManagerFactory emf;

    public T find(URI uri) {
        Objects.requireNonNull(uri);
        final EntityManager em = entityManager();
        try {
            return findByUri(uri, em);
        } finally {
            em.close();
        }
    }

    T findByUri(URI uri, EntityManager em) {
        return em.find(type, uri);
    }

    public List<T> findAll() {
        final EntityManager em = entityManager();
        try {
            return findAll(em);
        } finally {
            em.close();
        }
    }

    List<T> findAll(EntityManager em) {
        return em.createNativeQuery("SELECT ?x WHERE { ?x a ?type . }", type).setParameter("type", typeUri)
                 .getResultList();
    }

    public void persist(T entity) {
        Objects.requireNonNull(entity);
        final EntityManager em = entityManager();
        try {
            em.getTransaction().begin();
            persist(entity, em);
            em.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Error when persisting entity.", e);
            throw new PersistenceException(e);
        } finally {
            em.close();
        }
    }

    void persist(T entity, EntityManager em) {
        em.persist(entity);
    }

    public void update(T entity) {
        Objects.requireNonNull(entity);
        final EntityManager em = entityManager();
        try {
            em.getTransaction().begin();
            update(entity, em);
            em.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Error when updating entity.", e);
            throw new PersistenceException(e);
        } finally {
            em.close();
        }
    }

    void update(T entity, EntityManager em) {
        em.merge(entity);
    }

    public void remove(T entity) {
        Objects.requireNonNull(entity);
        final EntityManager em = entityManager();
        try {
            em.getTransaction().begin();
            remove(entity, em);
            em.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Error when removing entity.", e);
            throw new PersistenceException(e);
        } finally {
            em.close();
        }
    }

    void remove(T entity, EntityManager em) {
        final T toRemove = em.merge(entity);
        assert toRemove != null;
        em.remove(toRemove);
    }

    public boolean exists(URI uri) {
        if (uri == null) {
            return false;
        }
        final EntityManager em = entityManager();
        try {
            return exists(uri, em);
        } finally {
            em.close();
        }
    }

    boolean exists(URI uri, EntityManager em) {
        if (uri == null) {
            return false;
        }
        final String owlClass = type.getDeclaredAnnotation(OWLClass.class).iri();
        return em.createNativeQuery("ASK { ?individual a ?type . }", Boolean.class).setParameter("individual", uri)
                 .setParameter("type", URI.create(owlClass)).getSingleResult();
    }

    EntityManager entityManager() {
        return emf.createEntityManager();
    }
}
