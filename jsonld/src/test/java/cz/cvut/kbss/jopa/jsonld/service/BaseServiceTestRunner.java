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
package cz.cvut.kbss.jopa.jsonld.service;

import cz.cvut.kbss.jopa.jsonld.environment.Generator;
import cz.cvut.kbss.jopa.jsonld.environment.TestPersistenceConfig;
import cz.cvut.kbss.jopa.jsonld.environment.TestServiceConfig;
import cz.cvut.kbss.jopa.jsonld.environment.TestUtils;
import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.UserDao;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestPersistenceConfig.class, TestServiceConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class BaseServiceTestRunner {

    @Autowired
    protected UserDao userDao;

    @Autowired
    private PlatformTransactionManager txManager;

    protected User author;

    protected void setUp() {
        this.author = Generator.generateUser();
        executeInTransaction(() -> userDao.persist(author));
    }

    public void executeInTransaction(Runnable procedure) {
        TestUtils.executeInTransaction(txManager, procedure);
    }
}
