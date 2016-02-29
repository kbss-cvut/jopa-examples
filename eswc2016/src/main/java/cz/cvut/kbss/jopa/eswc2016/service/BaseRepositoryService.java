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
