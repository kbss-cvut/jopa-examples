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

import cz.cvut.kbss.jopa.jsonld.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao extends DerivableUriDao<User> {

    private final OrganizationDao organizationDao;

    @Autowired
    public UserDao(OrganizationDao organizationDao) {
        super(User.class);
        this.organizationDao = organizationDao;
    }

    @Override
    public void persist(User entity) {
        if (entity.getClinic() != null && !organizationDao.exists(entity.getClinic().getUri())) {
            organizationDao.persist(entity.getClinic());
        }
        super.persist(entity);
    }

    @Override
    public void update(User entity) {
        if (entity.getClinic() != null && !organizationDao.exists(entity.getClinic().getUri())) {
            organizationDao.persist(entity.getClinic());
        }
        super.update(entity);
    }
}
