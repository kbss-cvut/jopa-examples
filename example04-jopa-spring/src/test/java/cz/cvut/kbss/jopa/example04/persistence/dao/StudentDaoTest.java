package cz.cvut.kbss.jopa.example04.persistence.dao;

import cz.cvut.kbss.jopa.example04.environment.TestPersistenceConfig;
import cz.cvut.kbss.jopa.example04.environment.TransactionalTestRunner;
import cz.cvut.kbss.jopa.example04.model.Student;
import cz.cvut.kbss.jopa.example04.model.Vocabulary;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@ContextConfiguration(classes = {TestPersistenceConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentDaoTest extends TransactionalTestRunner {

    @Autowired
    private EntityManager em;

    @Autowired
    private StudentDao sut;

    @Test
    void findByKeyFindsInstanceWithMatchingKey() {
        final Student student = new Student();
        student.setFirstName("Thomas");
        student.setLastName("Lasky");
        student.setEmail("lasky@cams.org");
        student.setUri(URI.create(Vocabulary.Student + "/thomas-lasky"));
        student.setKey("12345");
        transactional(() -> em.persist(student));

        final Student result = sut.findByKey(student.getKey());
        assertNotNull(result);
        assertEquals(student, result);
    }

    @Test
    void findByKeyReturnsNullForUnknownKey() {
        assertNull(sut.findByKey("12345"));
    }
}
