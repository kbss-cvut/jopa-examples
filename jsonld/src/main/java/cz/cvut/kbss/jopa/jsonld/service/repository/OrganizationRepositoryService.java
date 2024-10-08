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
package cz.cvut.kbss.jopa.jsonld.service.repository;

import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.BaseDao;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.OrganizationDao;
import cz.cvut.kbss.jopa.jsonld.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrganizationRepositoryService extends BaseRepositoryService<Organization> implements OrganizationService {

    private final OrganizationDao organizationDao;

    @Autowired
    public OrganizationRepositoryService(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    @Override
    protected BaseDao<Organization> getPrimaryDao() {
        return organizationDao;
    }

    @Override
    protected void prePersist(Organization instance) {
        instance.setDateCreated(new Date());
    }
}
