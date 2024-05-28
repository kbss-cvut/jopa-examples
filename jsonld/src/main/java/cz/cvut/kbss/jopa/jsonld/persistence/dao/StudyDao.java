/*
 * JOPA Examples
 * Copyright (C) 2024 Czech Technical University in Prague
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
package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.model.Study;
import cz.cvut.kbss.jopa.jsonld.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Component
public class StudyDao extends BaseDao<Study> {

    private final UserDao userDao;

    @Autowired
    public StudyDao(UserDao userDao) {
        super(Study.class);
        this.userDao = userDao;
    }

    @Override
    public void persist(Study entity) {
        persistUsersIfNecessary(entity);
        assert entity.getAuthor() != null;
        super.persist(entity);
    }

    private void persistUsersIfNecessary(Study entity) {
        if (entity.getAdministrators() != null) {
            entity.getAdministrators().stream().filter(u -> !userDao.exists(u.getUri())).forEach(userDao::persist);
        }
        // Ensure the participant does not exist or has not been just persisted as admin
        // Prevents issues with repeated persists when multiple objects created by DTO mapper represent the same entity
        if (entity.getParticipants() != null) {
            final Set<User> admins =
                    entity.getAdministrators() != null ? entity.getAdministrators() : Collections.emptySet();
            entity.getParticipants().stream()
                  .filter(u -> !userDao.exists(u.getUri()) && admins.stream().noneMatch(
                          admin -> Objects.equals(u.getUri(), admin.getUri()))).forEach(userDao::persist);
        }
    }

    @Override
    public void update(Study entity) {
        persistUsersIfNecessary(entity);
        super.update(entity);
    }
}