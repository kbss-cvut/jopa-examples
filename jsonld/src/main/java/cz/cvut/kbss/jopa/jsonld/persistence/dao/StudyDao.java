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
package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.model.Study;
import cz.cvut.kbss.jopa.jsonld.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

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
        persistUsersIfNecessary(entity.getAdministrators());
        persistUsersIfNecessary(entity.getParticipants());
        assert entity.getAuthor() != null;
        super.persist(entity);
    }

    private void persistUsersIfNecessary(Collection<User> users) {
        if (users != null && !users.isEmpty()) {
            users.stream().filter(admin -> !userDao.exists(admin.getUri()))
                 .forEach(userDao::persist);
        }
    }

    @Override
    public void update(Study entity) {
        persistUsersIfNecessary(entity.getAdministrators());
        persistUsersIfNecessary(entity.getParticipants());
        super.update(entity);
    }
}
