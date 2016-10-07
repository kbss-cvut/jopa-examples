package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.environment.Generator;
import cz.cvut.kbss.jopa.jsonld.model.Study;
import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


public class StudyDaoTest extends BaseDaoTestRunner {

    @Autowired
    private StudyDao studyDao;

    @Autowired
    private EntityManagerFactory emf;

    private User author;

    @Before
    public void setUp() throws Exception {
        this.author = Generator.generateUser();
        author.generateUri();
        final EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(author);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void persistPersistsUsersWhenTheyDoNotExist() {
        final Study study = Generator.generateStudy(true);
        study.setAuthor(author);
        final User admin = Generator.generateUser();
        admin.setClinic(Generator.generateOrganization());
        study.setAdministrators(Collections.singleton(admin));

        for (int i = 0; i < Generator.randomPositiveInt(5, 10); i++) {
            study.addParticipant(Generator.generateUser());
        }

        studyDao.persist(study);

        final List<User> expectedUsers = new ArrayList<>();
        expectedUsers.addAll(study.getAdministrators());
        expectedUsers.addAll(study.getParticipants());
        final EntityManager em = emf.createEntityManager();
        try {
            for (User expected : expectedUsers) {
                assertNotNull(em.find(User.class, expected.getUri()));
            }
            final Study result = studyDao.find(study.getUri());
            assertEquals(study.getAdministrators().size(), result.getAdministrators().size());
            assertTrue(study.getAdministrators().containsAll(result.getAdministrators()));
            assertEquals(study.getParticipants().size(), result.getParticipants().size());
            assertTrue(study.getParticipants().containsAll(result.getParticipants()));
        } finally {
            em.close();
        }
    }
}