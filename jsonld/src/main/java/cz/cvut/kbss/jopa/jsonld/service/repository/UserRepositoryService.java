package cz.cvut.kbss.jopa.jsonld.service.repository;

import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.BaseDao;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryService extends BaseRepositoryService<User> {

    @Autowired
    private UserDao userDao;

    @Override
    protected BaseDao<User> getPrimaryDao() {
        return userDao;
    }
}
