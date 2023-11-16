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
package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.environment.Generator;
import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizationDaoTest extends BaseDaoTestRunner {

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private EntityManager em;

    @Test
    public void persistGeneratesKey() {
        final Organization organization = Generator.generateOrganization();
        executeInTransaction(() -> {
            organizationDao.persist(organization);
            assertNotNull(organization.getKey());
        });

        assertNotNull(em.find(Organization.class, organization.getUri()));
    }

    @Test
    public void findByKeyFindsInstanceWithSpecifiedKey() {
        final Organization organization = Generator.generateOrganization();
        executeInTransaction(() -> organizationDao.persist(organization));

        final Organization result = organizationDao.findByKey(organization.getKey());
        assertNotNull(result);
        assertEquals(organization.getUri(), result.getUri());
    }

    @Test
    public void findByKeyReturnsNullForUnknownKey() {
        assertNull(organizationDao.findByKey(UUID.randomUUID().toString()));
    }

    @Test
    public void persistCollectionPersistsAllItems() {
        final List<Organization> organizations = new ArrayList<>();
        for (int i = 0; i < Generator.randomPositiveInt(5, 10); i++) {
            organizations.add(Generator.generateOrganization());
        }
        executeInTransaction(() -> organizationDao.persist(organizations));
        organizations.forEach(o -> assertNotNull(organizationDao.find(o.getUri())));
    }
}
