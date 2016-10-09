package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.model.Study;
import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class StudyDao extends BaseDao<Study> {

    @Autowired
    private UserDao userDao;

    public StudyDao() {
        super(Study.class);
    }

    @Override
    void persist(Study entity, EntityManager em) {
        persistUsersIfNecessary(entity.getAdministrators(), em);
        persistUsersIfNecessary(entity.getParticipants(), em);
        assert entity.getAuthor() != null;
        assert userDao.exists(entity.getAuthor().getUri(), em);
        super.persist(entity, em);
    }

    private void persistUsersIfNecessary(Collection<User> users, EntityManager em) {
        if (users != null && !users.isEmpty()) {
            users.stream().filter(admin -> !userDao.exists(admin.getUri(), em))
                 .forEach(admin -> userDao.persist(admin, em));
        }
    }

    @Override
    void update(Study entity, EntityManager em) {
        persistUsersIfNecessary(entity.getAdministrators(), em);
        persistUsersIfNecessary(entity.getParticipants(), em);
        super.update(entity, em);
    }
}
