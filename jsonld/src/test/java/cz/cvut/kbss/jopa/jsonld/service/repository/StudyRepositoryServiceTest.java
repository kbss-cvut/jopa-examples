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
package cz.cvut.kbss.jopa.jsonld.service.repository;

import cz.cvut.kbss.jopa.jsonld.environment.Generator;
import cz.cvut.kbss.jopa.jsonld.model.Study;
import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.jsonld.service.BaseServiceTestRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class StudyRepositoryServiceTest extends BaseServiceTestRunner {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private StudyRepositoryService studyService;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void persistSetsDateCreated() {
        final Study study = Generator.generateStudy(false);
        study.setAuthor(author);
        assertNull(study.getCreated());
        studyService.persist(study);

        final Study result = studyService.find(study.getUri());
        assertNotNull(result.getCreated());
        assertEquals(author, result.getAuthor());
    }

    @Test
    public void persistSetsDefaultAuthorWhenStudyAuthorIsMissing() {
        final Study study = Generator.generateStudy(false);
        study.setAuthor(null);
        studyService.persist(study);
        assertNotNull(study.getAuthor());

        final Study result = studyService.find(study.getUri());
        assertEquals(study.getAuthor(), result.getAuthor());
    }

    @Test
    public void updateSetsLastModified() {
        final Study study = Generator.generateStudy(false);
        study.setAuthor(author);
        studyService.persist(study);

        study.setName("UpdatedName");
        assertNull(study.getLastModified());
        studyService.update(study);

        final Study result = studyService.find(study.getUri());
        assertNotNull(result.getLastModified());
    }

    @Test
    public void updateThrowsIllegalArgumentExceptionWhenAuthorIsChanged() {
        final Study study = Generator.generateStudy(false);
        study.setAuthor(author);
        studyService.persist(study);

        final User newAuthor = Generator.generateUser();
        executeInTransaction(() -> userDao.persist(newAuthor));

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Study author cannot be changed.");
        study.setAuthor(newAuthor);
        studyService.update(study);
    }
}
