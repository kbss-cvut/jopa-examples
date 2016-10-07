package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao extends DerivableUriDao<User> {

    @Autowired
    private OrganizationDao organizationDao;

    public UserDao() {
        super(User.class);
    }

    @Override
    void persist(User entity, EntityManager em) {
        if (entity.getClinic() != null && !organizationDao.exists(entity.getClinic().getUri(), em)) {
            organizationDao.persist(entity.getClinic(), em);
        }
        super.persist(entity, em);
    }

    @Override
    void update(User entity, EntityManager em) {
        if (entity.getClinic() != null && !organizationDao.exists(entity.getClinic().getUri(), em)) {
            organizationDao.persist(entity.getClinic(), em);
        }
        super.update(entity, em);
    }
}
