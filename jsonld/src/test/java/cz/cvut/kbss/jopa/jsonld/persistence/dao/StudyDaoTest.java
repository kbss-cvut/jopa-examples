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
import cz.cvut.kbss.jopa.jsonld.model.Study;
import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudyDaoTest extends BaseDaoTestRunner {

    @Autowired
    private StudyDao studyDao;

    @Autowired
    private EntityManager em;

    private User author;

    @BeforeEach
    public void setUp() {
        this.author = Generator.generateUser();
        author.generateUri();
        author.setKey(Long.toString(System.currentTimeMillis()));
        executeInTransaction(() -> em.persist(author));
    }

    @Test
    public void persistPersistsUsersWhenTheyDoNotExist() {
        final Study study = generateStudyWithUsers();

        executeInTransaction(() -> studyDao.persist(study));

        final List<User> expectedUsers = new ArrayList<>();
        expectedUsers.addAll(study.getAdministrators());
        expectedUsers.addAll(study.getParticipants());
        for (User expected : expectedUsers) {
            assertNotNull(em.find(User.class, expected.getUri()));
        }
        final Study result = studyDao.find(study.getUri());
        verifyStudyUsers(study, result);
    }

    private void verifyStudyUsers(Study study, Study result) {
        assertEquals(study.getAdministrators().size(), result.getAdministrators().size());
        assertTrue(study.getAdministrators().containsAll(result.getAdministrators()));
        assertEquals(study.getParticipants().size(), result.getParticipants().size());
        assertTrue(study.getParticipants().containsAll(result.getParticipants()));
    }

    private Study generateStudyWithUsers() {
        final Study study = Generator.generateStudy(true);
        study.setAuthor(author);
        final User admin = Generator.generateUser();
        admin.setClinic(Generator.generateOrganization());
        study.setAdministrators(Collections.singleton(admin));

        for (int i = 0; i < Generator.randomPositiveInt(5, 10); i++) {
            study.addParticipant(Generator.generateUser());
        }
        return study;
    }

    @Test
    public void updatePersistsUsersWhenTheyDoNotExist() {
        final Study study = generateStudyWithUsers();
        executeInTransaction(() -> studyDao.persist(study));

        for (int i = 0; i < Generator.randomPositiveInt(2, 5); i++) {
            study.addParticipant(Generator.generateUser());
        }
        executeInTransaction(() -> studyDao.update(study));

        final Study result = studyDao.find(study.getUri());
        verifyStudyUsers(study, result);
    }

    @Test
    public void generatesKeyOnPersist() {
        final Study study = Generator.generateStudy(true);
        study.setAuthor(author);
        assertNull(study.getKey());
        executeInTransaction(() -> studyDao.persist(study));
        assertNotNull(study.getKey());

        final Study result = studyDao.findByKey(study.getKey());
        assertNotNull(result);
        assertEquals(study.getUri(), result.getUri());
    }
}
