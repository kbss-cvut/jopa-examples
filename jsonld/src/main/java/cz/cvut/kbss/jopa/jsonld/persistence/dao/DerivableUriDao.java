package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.model.AbstractEntity;
import cz.cvut.kbss.jopa.jsonld.model.util.HasDerivableUri;
import cz.cvut.kbss.jopa.model.EntityManager;

public class DerivableUriDao<T extends AbstractEntity & HasDerivableUri> extends BaseDao<T> {

    DerivableUriDao(Class<T> type) {
        super(type);
    }

    @Override
    void persist(T entity, EntityManager em) {
        entity.generateUri();
        super.persist(entity, em);
    }
}
