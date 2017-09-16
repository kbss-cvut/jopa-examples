package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.model.Study;
import cz.cvut.kbss.jopa.jsonld.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class StudyDao extends BaseDao<Study> {

    private final UserDao userDao;

    @Autowired
    public StudyDao(UserDao userDao) {
        super(Study.class);
        this.userDao = userDao;
    }

    @Override
    public void persist(Study entity) {
        persistUsersIfNecessary(entity.getAdministrators());
        persistUsersIfNecessary(entity.getParticipants());
        assert entity.getAuthor() != null;
        super.persist(entity);
    }

    private void persistUsersIfNecessary(Collection<User> users) {
        if (users != null && !users.isEmpty()) {
            users.stream().filter(admin -> !userDao.exists(admin.getUri()))
                 .forEach(userDao::persist);
        }
    }

    @Override
    public void update(Study entity) {
        persistUsersIfNecessary(entity.getAdministrators());
        persistUsersIfNecessary(entity.getParticipants());
        super.update(entity);
    }
}
