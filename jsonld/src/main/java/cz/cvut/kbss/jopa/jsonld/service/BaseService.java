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

    void persist(Collection<T> instances);

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
