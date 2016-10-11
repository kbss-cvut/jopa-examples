package cz.cvut.kbss.jopa.jsonld.service.repository;

import cz.cvut.kbss.jopa.jsonld.environment.Generator;
import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.jsonld.service.BaseServiceTestRunner;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserRepositoryServiceTest extends BaseServiceTestRunner {

    @Autowired
    private UserRepositoryService userService;

    @Test
    public void persistSetsDateCreated() {
        final User user = Generator.generateUser();
        user.setDateCreated(null);
        userService.persist(user);
        assertNotNull(user.getDateCreated());

        final User result = userService.findByKey(user.getKey());
        assertEquals(user.getDateCreated(), result.getDateCreated());
    }
}
