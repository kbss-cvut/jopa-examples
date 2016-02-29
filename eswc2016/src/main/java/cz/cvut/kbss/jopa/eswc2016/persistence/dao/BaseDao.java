package cz.cvut.kbss.jopa.eswc2016.persistence.dao;

import cz.cvut.kbss.jopa.eswc2016.model.Vocabulary;
import cz.cvut.kbss.jopa.eswc2016.persistence.PersistenceException;
import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.List;
import java.util.Objects;

public abstract class BaseDao<T> {

    private static final Logger LOG = LoggerFactory.getLogger(BaseDao.class);

    protected final Class<T> type;
    protected final URI typeUri;

    protected BaseDao(Class<T> type) {
        this.type = type;
        final OWLClass owlClass = type.getDeclaredAnnotation(OWLClass.class);
        if (owlClass == null) {
            throw new IllegalArgumentException("Class " + type + " is not an entity.");
        }
        this.typeUri = URI.create(owlClass.iri());
    }

    @Autowired
    private EntityManagerFactory emf;

    public T find(String uri) {
        Objects.requireNonNull(uri);
        final EntityManager em = entityManager();
        try {
            return findByUri(uri, em);
        } finally {
            em.close();
        }
    }

    protected T findByUri(String uri, EntityManager em) {
        return em.find(type, uri);
    }

    public T findByKey(Long key) {
        Objects.requireNonNull(key);
        final EntityManager em = entityManager();
        try {
            return findByKey(key, em);
        } finally {
            em.close();
        }
    }

    protected T findByKey(Long key, EntityManager em) {
        try {
            return em.createNativeQuery("SELECT ?x WHERE { ?x <" + Vocabulary.s_p_identifier + "> ?key ;" +
                    "a ?type }", type)
                     .setParameter("key", key).setParameter("type", typeUri).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<T> findAll() {
        final EntityManager em = entityManager();
        try {
            return findAll(em);
        } finally {
            em.close();
        }
    }

    protected List<T> findAll(EntityManager em) {
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

    protected void persist(T entity, EntityManager em) {
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

    protected void update(T entity, EntityManager em) {
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

    protected void remove(T entity, EntityManager em) {
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

    protected boolean exists(URI uri, EntityManager em) {
        if (uri == null) {
            return false;
        }
        final String owlClass = type.getDeclaredAnnotation(OWLClass.class).iri();
        return em.createNativeQuery("ASK { ?individual a ?type . }", Boolean.class).setParameter("individual", uri)
                 .setParameter("type", URI.create(owlClass)).getSingleResult();
    }

    protected EntityManager entityManager() {
        return emf.createEntityManager();
    }
}
