package cz.cvut.kbss.jopa.jsonld.service;

import cz.cvut.kbss.jopa.jsonld.model.AbstractEntity;

import java.net.URI;
import java.util.Collection;
import java.util.List;

/**
 * Provides basic CRUD operations.
 *
 * @param <T>
 */
public interface BaseService<T extends AbstractEntity> {

    List<T> findAll();

    T find(URI uri);

    T findByKey(String key);

    void persist(T instance);

    void update(T instance);

    void remove(T instance);

    /**
     * Checks whether instance with the specified URI exists.
     *
     * @param uri Instance URI
     * @return Whether a matching instance exists
     */
    boolean exists(URI uri);
}
