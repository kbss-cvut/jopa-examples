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

    @Autowired
    private PropertiesValidationService propertiesValidation;

    @Override
    protected BaseDao<Event> getPrimaryDao() {
        return eventDao;
    }

    public Event findByKey(Long key) {
        Objects.requireNonNull(key);
        return eventDao.findByKey(key);
    }

    @Override
    public void persist(Event instance) {
        Objects.requireNonNull(instance);
        propertiesValidation.validate(instance.getProperties());
        super.persist(instance);
    }

    @Override
    public void update(Event instance) {
        Objects.requireNonNull(instance);
        propertiesValidation.validate(instance.getProperties());
        super.update(instance);
    }
}
