/*
 * JOPA Examples
 * Copyright (C) 2023 Czech Technical University in Prague
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
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

    @Override
    protected Event findByKey(Long key, EntityManager em) {
        final Event e = super.findByKey(key, em);
        if (e != null) {
            // Trigger the lazy field
            e.getIsDocumentedBy();
        }
        return e;
    }
}
