package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.model.AbstractEntity;
import cz.cvut.kbss.jopa.jsonld.model.util.HasDerivableUri;

public class DerivableUriDao<T extends AbstractEntity & HasDerivableUri> extends BaseDao<T> {

    DerivableUriDao(Class<T> type) {
        super(type);
    }

    @Override
    public void persist(T entity) {
        entity.generateUri();
        super.persist(entity);
    }
}
