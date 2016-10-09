package cz.cvut.kbss.jopa.jsonld.service;

import cz.cvut.kbss.jopa.jsonld.environment.Generator;
import cz.cvut.kbss.jopa.jsonld.environment.TestPersistenceConfig;
import cz.cvut.kbss.jopa.jsonld.environment.TestServiceConfig;
import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.UserDao;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestPersistenceConfig.class, TestServiceConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BaseServiceTestRunner {

    @Autowired
    protected UserDao userDao;

    protected User author;

    protected void setUp() {
        this.author = Generator.generateUser();
        userDao.persist(author);
    }
}
