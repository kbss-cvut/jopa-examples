package cz.cvut.kbss.jopa.eswc2016.persistence.dao;

import cz.cvut.kbss.jopa.eswc2016.model.model.Event;
import org.springframework.stereotype.Repository;

@Repository
public class EventDao extends BaseDao<Event> {

    public EventDao() {
        super(Event.class);
    }
}
