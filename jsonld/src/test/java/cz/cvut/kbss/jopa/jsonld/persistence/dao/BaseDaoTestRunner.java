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

import cz.cvut.kbss.jopa.jsonld.environment.TestPersistenceConfig;
import cz.cvut.kbss.jopa.jsonld.environment.TestUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestPersistenceConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class BaseDaoTestRunner {

    @Autowired
    private PlatformTransactionManager txManager;

    /**
     * Since JOPA does not understand SPARQL queries, any DAO method using a query will not be able to see uncommitted
     * transactional changes. So the whole test cannot run in a single transaction, as is common in regular Spring testing.
     * <p>
     * Instead, we need to perform methods which change the state of the storage in transactions, so that the changes are
     * really committed into the storage.
     *
     * @param procedure Code to execute
     */
    public void executeInTransaction(Runnable procedure) {
        TestUtils.executeInTransaction(txManager, procedure);
    }
}
