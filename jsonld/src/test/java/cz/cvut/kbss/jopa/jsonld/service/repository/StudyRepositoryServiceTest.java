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
        userDao.persist(newAuthor);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Study author cannot be changed.");
        study.setAuthor(newAuthor);
        studyService.update(study);
    }
}
