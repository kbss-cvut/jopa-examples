package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.environment.Generator;
import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.Assert.*;

public class OrganizationDaoTest extends BaseDaoTestRunner {

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private EntityManagerFactory emf;

    @Test
    public void persistGeneratesKey() {
        final Organization organization = Generator.generateOrganization();
        organizationDao.persist(organization);
        assertNotNull(organization.getKey());

        final EntityManager em = emf.createEntityManager();
        try {
            assertNotNull(em.find(Organization.class, organization.getUri()));
        } finally {
            em.close();
        }
    }

    @Test
    public void findByKeyFindsInstanceWithSpecifiedKey() {
        final Organization organization = Generator.generateOrganization();
        organizationDao.persist(organization);

        final Organization result = organizationDao.findByKey(organization.getKey());
        assertNotNull(result);
        assertEquals(organization.getUri(), result.getUri());
    }

    @Test
    public void findByKeyReturnsNullForUnknownKey() {
        assertNull(organizationDao.findByKey(UUID.randomUUID().toString()));
    }
}
