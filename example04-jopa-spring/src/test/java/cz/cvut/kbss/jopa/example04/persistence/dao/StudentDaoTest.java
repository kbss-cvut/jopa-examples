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
package cz.cvut.kbss.jopa.example04.persistence.dao;

import cz.cvut.kbss.jopa.example04.environment.TestPersistenceConfig;
import cz.cvut.kbss.jopa.example04.environment.TransactionalTestRunner;
import cz.cvut.kbss.jopa.example04.model.Student;
import cz.cvut.kbss.jopa.example04.model.Vocabulary;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@EnableTransactionManagement
@ContextConfiguration(classes = {TestPersistenceConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentDaoTest extends TransactionalTestRunner {

    @Autowired
    private EntityManager em;

    @Autowired
    private StudentDao sut;

    @Test
    void findByKeyFindsInstanceWithMatchingKey() {
        final Student student = new Student();
        student.setFirstName("Thomas");
        student.setLastName("Lasky");
        student.setEmail("lasky@cams.org");
        student.setUri(URI.create(Vocabulary.Student + "/thomas-lasky"));
        student.setKey("12345");
        transactional(() -> em.persist(student));

        final Student result = sut.findByKey(student.getKey());
        assertNotNull(result);
        assertEquals(student, result);
    }

    @Test
    void findByKeyReturnsNullForUnknownKey() {
        assertNull(sut.findByKey("12345"));
    }
}
