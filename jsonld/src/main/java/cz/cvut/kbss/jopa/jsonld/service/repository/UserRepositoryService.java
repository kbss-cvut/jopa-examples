package cz.cvut.kbss.jopa.jsonld.service.repository;

import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.jsonld.model.Vocabulary;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.BaseDao;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.UserDao;
import cz.cvut.kbss.jopa.jsonld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Date;

@Service
public class UserRepositoryService extends BaseRepositoryService<User> implements UserService {

    private static final URI DEFAULT_USER_URI = URI
            .create(Vocabulary.ONTOLOGY_IRI_study_manager + "/System+Administrator");

    @Autowired
    private UserDao userDao;

    private User defaultUser;

    @Override
    protected BaseDao<User> getPrimaryDao() {
        return userDao;
    }

    @Override
    protected void prePersist(User instance) {
        instance.setDateCreated(new Date());
    }

    @Override
    public User getDefaultUser() {
        return defaultUser;
    }

    @PostConstruct
    private void initDefaultUser() {
        if (!userDao.exists(DEFAULT_USER_URI)) {
            this.defaultUser = new User();
            defaultUser.setUri(DEFAULT_USER_URI);
            defaultUser.setFirstName("System");
            defaultUser.setLastName("Administrator");
            userDao.persist(defaultUser);
        } else {
            this.defaultUser = userDao.find(DEFAULT_USER_URI);
        }
    }
}
