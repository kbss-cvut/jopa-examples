package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.environment.Generator;
import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserDaoTest extends BaseDaoTestRunner {

    @Autowired
    private UserDao dao;

    @Autowired
    private EntityManagerFactory emf;

    @Test
    public void persistGeneratesUri() {
        final User user = Generator.generateUser();
        dao.persist(user);

        assertNotNull(user.getUri());
        final EntityManager em = emf.createEntityManager();
        try {
            final User result = em.find(User.class, user.getUri());
            assertNotNull(result);
        } finally {
            em.close();
        }
    }

    @Test
    public void persistPersistsOrganizationIfItDoesNotExistYet() {
        final User user = Generator.generateUser();
        user.setClinic(Generator.generateOrganization());
        dao.persist(user);

        final EntityManager em = emf.createEntityManager();
        try {
            final Organization result = em.find(Organization.class, user.getClinic().getUri());
            assertNotNull(result);
        } finally {
            em.close();
        }
    }

    @Test
    public void updatePersistsOrganizationIfItDoesNotExistYet() {
        final User user = Generator.generateUser();
        final Organization oldOrganization = Generator.generateOrganization();
        user.setClinic(oldOrganization);
        dao.persist(user);

        final Organization newOrganization = Generator.generateOrganization();
        user.setClinic(newOrganization);
        dao.update(user);

        final EntityManager em = emf.createEntityManager();
        try {
            assertNotNull(em.find(Organization.class, oldOrganization.getUri()));
            assertNotNull(em.find(Organization.class, newOrganization.getUri()));
            final User result = dao.find(user.getUri());
            assertEquals(newOrganization, result.getClinic());
        } finally {
            em.close();
        }
    }
}
