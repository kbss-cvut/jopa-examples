package cz.cvut.kbss.jopa.jsonld.service.repository;

import cz.cvut.kbss.jopa.jsonld.environment.Generator;
import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.jsonld.service.BaseServiceTestRunner;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrganizationRepositoryServiceTest extends BaseServiceTestRunner {

    @Autowired
    private OrganizationRepositoryService organizationService;

    @Test
    public void persistSetsDateCreated() {
        final Organization org = Generator.generateOrganization();
        org.setDateCreated(null);
        organizationService.persist(org);
        assertNotNull(org.getDateCreated());

        final Organization result = organizationService.find(org.getUri());
        assertEquals(org.getDateCreated(), result.getDateCreated());
    }
}
