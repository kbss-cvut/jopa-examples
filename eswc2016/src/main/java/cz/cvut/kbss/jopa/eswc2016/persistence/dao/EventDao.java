package cz.cvut.kbss.jopa.eswc2016.persistence.dao;

import cz.cvut.kbss.jopa.eswc2016.model.model.Event;
import cz.cvut.kbss.jopa.eswc2016.util.KeyGenerator;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class EventDao extends BaseDao<Event> {

    public EventDao() {
        super(Event.class);
    }

    @Override
    protected void persist(Event entity, EntityManager em) {
        entity.setIdentifier(KeyGenerator.generateKey());
        super.persist(entity, em);
    }
}
