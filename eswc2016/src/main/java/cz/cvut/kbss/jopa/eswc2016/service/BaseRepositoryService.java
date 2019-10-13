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
package cz.cvut.kbss.jopa.eswc2016.service;

import cz.cvut.kbss.jopa.eswc2016.persistence.dao.BaseDao;

import java.util.List;

public abstract class BaseRepositoryService<T> {

    protected abstract BaseDao<T> getPrimaryDao();

    public List<T> findAll() {
        return getPrimaryDao().findAll();
    }

    public T find(String uri) {
        return getPrimaryDao().find(uri);
    }

    public void persist(T instance) {
        getPrimaryDao().persist(instance);
    }

    public void update(T instance) {
        getPrimaryDao().update(instance);
    }

    public void remove(T instance) {
        getPrimaryDao().remove(instance);
    }
}
