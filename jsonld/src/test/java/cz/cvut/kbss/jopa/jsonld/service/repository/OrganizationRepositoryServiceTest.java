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
package cz.cvut.kbss.jopa.jsonld.service.repository;

import cz.cvut.kbss.jopa.jsonld.environment.Generator;
import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.jsonld.service.BaseServiceTestRunner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrganizationRepositoryServiceTest extends BaseServiceTestRunner {

    @Autowired
    private OrganizationRepositoryService organizationService;

    @Test
    public void persistSetsDateCreated() {
        final Organization org = Generator.generateOrganization();
        org.setDateCreated(null);
        organizationService.persist(org);
        assertNotNull(org.getDateCreated());

        final Organization result = organizationService.find(org.getUri());
        assertEquals(org.getDateCreated(), result.getDateCreated());
    }
}
