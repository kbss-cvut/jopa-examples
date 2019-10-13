/**
 * Copyright (C) 2019 Czech Technical University in Prague
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.jopa.jsonld.service.repository;

import cz.cvut.kbss.jopa.jsonld.model.AbstractEntity;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.BaseDao;
import cz.cvut.kbss.jopa.jsonld.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Base implementation of the {@link BaseService}.
 * <p>
 * It provides hooks for some of the operations so that subclasses don't have to override the main CRUD operations,
 * but instead use these hooks to implement their specific tasks.
 *
 * @param <T>
 */
public abstract class BaseRepositoryService<T extends AbstractEntity> implements BaseService<T> {

    protected abstract BaseDao<T> getPrimaryDao();

    @Transactional
    @Override
    public List<T> findAll() {
        final List<T> result = getPrimaryDao().findAll();
        result.forEach(this::postLoad);
        return result;
    }

    @Transactional
    @Override
    public T find(URI uri) {
        final T result = getPrimaryDao().find(uri);
        postLoad(result);
        return result;
    }

    @Transactional
    @Override
    public T findByKey(String key) {
        final T result = getPrimaryDao().findByKey(key);
        postLoad(result);
        return result;
    }

    @Transactional
    @Override
    public void persist(T instance) {
        Objects.requireNonNull(instance);
        prePersist(instance);
        getPrimaryDao().persist(instance);
    }

    @Transactional
    @Override
    public void persist(Collection<T> instances) {
        Objects.requireNonNull(instances);
        instances.forEach(this::prePersist);
        getPrimaryDao().persist(instances);
    }

    @Transactional
    @Override
    public void update(T instance) {
        Objects.requireNonNull(instance);
        preUpdate(instance);
        getPrimaryDao().update(instance);
    }

    @Transactional
    @Override
    public void remove(T instance) {
        getPrimaryDao().remove(instance);
    }

    @Transactional
    @Override
    public boolean exists(URI uri) {
        return getPrimaryDao().exists(uri);
    }

    /**
     * Hook for additional business logic to be performed before the specified instance is persisted.
     * <p>
     * Does nothing by default and is intended to be overridden.
     *
     * @param instance The instance to persist, never {@code null}
     */
    protected void prePersist(T instance) {
        // Do nothing, intended for overriding
    }

    /**
     * Hook for additional business logic to be performed before the specified instance is merged into the ontology as
     * update of an existing record.
     * <p>
     * Does nothing by default and is intended to be overridden.
     *
     * @param instance The instance with updated data, never {@code null}
     */
    protected void preUpdate(T instance) {
        // Do nothing, intended for overriding
    }

    /**
     * Hook for additional business logic to be performed after an instance is loaded.
     * <p>
     * Does nothing by default and is intended to be overridden.
     *
     * @param instance The loaded instance, possibly {@code null}
     */
    protected void postLoad(T instance) {
        // Do nothing, intended for overriding
    }
}
