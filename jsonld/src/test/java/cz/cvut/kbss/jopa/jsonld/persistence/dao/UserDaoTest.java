package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.environment.Generator;
import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserDaoTest extends BaseDaoTestRunner {

    @Autowired
    private UserDao dao;

    @Autowired
    private EntityManager em;

    @Test
    public void persistGeneratesUri() {
        final User user = Generator.generateUser();
        executeInTransaction(() -> dao.persist(user));

        assertNotNull(user.getUri());
        final User result = em.find(User.class, user.getUri());
        assertNotNull(result);
    }

    @Test
    public void persistPersistsOrganizationIfItDoesNotExistYet() {
        final User user = Generator.generateUser();
        user.setClinic(Generator.generateOrganization());
        executeInTransaction(() -> dao.persist(user));

        final Organization result = em.find(Organization.class, user.getClinic().getUri());
        assertNotNull(result);
    }

    @Test
    public void updatePersistsOrganizationIfItDoesNotExistYet() {
        final User user = Generator.generateUser();
        final Organization oldOrganization = Generator.generateOrganization();
        user.setClinic(oldOrganization);
        executeInTransaction(() -> dao.persist(user));

        final Organization newOrganization = Generator.generateOrganization();
        user.setClinic(newOrganization);
        executeInTransaction(() -> dao.update(user));

        assertNotNull(em.find(Organization.class, oldOrganization.getUri()));
        assertNotNull(em.find(Organization.class, newOrganization.getUri()));
        final User result = dao.find(user.getUri());
        assertEquals(newOrganization, result.getClinic());
    }
}
