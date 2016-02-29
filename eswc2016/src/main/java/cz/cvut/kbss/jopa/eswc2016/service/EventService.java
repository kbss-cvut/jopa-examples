package cz.cvut.kbss.jopa.eswc2016.service;

import cz.cvut.kbss.jopa.eswc2016.model.model.Event;
import cz.cvut.kbss.jopa.eswc2016.persistence.dao.BaseDao;
import cz.cvut.kbss.jopa.eswc2016.persistence.dao.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EventService extends BaseRepositoryService<Event> {

    @Autowired
    private EventDao eventDao;

    @Override
    protected BaseDao<Event> getPrimaryDao() {
        return eventDao;
    }

    public Event findByKey(Long key) {
        Objects.requireNonNull(key);
        return eventDao.findByKey(key);
    }
}
