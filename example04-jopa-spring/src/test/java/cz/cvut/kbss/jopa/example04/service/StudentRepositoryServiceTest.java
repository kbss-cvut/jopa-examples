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
package cz.cvut.kbss.jopa.example04.service;

import cz.cvut.kbss.jopa.example04.config.ServiceConfig;
import cz.cvut.kbss.jopa.example04.environment.TestPersistenceConfig;
import cz.cvut.kbss.jopa.example04.environment.TransactionalTestRunner;
import cz.cvut.kbss.jopa.example04.model.Student;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@ContextConfiguration(classes = {TestPersistenceConfig.class, ServiceConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentRepositoryServiceTest extends TransactionalTestRunner {

    @Autowired
    private EntityManager em;

    @Autowired
    private StudentRepositoryService sut;

    @Test
    void persistGeneratesUri() {
        final Student student = new Student();
        student.setFirstName("Thomas");
        student.setLastName("Lasky");
        student.setEmail("thomas.lasky@cams.org");

        transactional(() -> sut.persist(student));

        assertNotNull(student.getUri());
        final Student result = em.find(Student.class, student.getUri());
        assertEquals(student, result);
    }

    @Test
    void persistGeneratesKey() {
        final Student student = new Student();
        student.setFirstName("Thomas");
        student.setLastName("Lasky");
        student.setEmail("thomas.lasky@cams.org");

        transactional(() -> sut.persist(student));

        assertNotNull(student.getKey());
        final Student result = em.createNamedQuery("Student.findByKey", Student.class)
                                 .setParameter("key", student.getKey()).getSingleResult();
        assertEquals(student, result);
    }
}
