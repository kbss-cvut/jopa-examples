package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao extends DerivableUriDao<User> {

    private final OrganizationDao organizationDao;

    @Autowired
    public UserDao(OrganizationDao organizationDao) {
        super(User.class);
        this.organizationDao = organizationDao;
    }

    @Override
    public void persist(User entity) {
        if (entity.getClinic() != null && !organizationDao.exists(entity.getClinic().getUri())) {
            organizationDao.persist(entity.getClinic());
        }
        super.persist(entity);
    }

    @Override
    public void update(User entity) {
        if (entity.getClinic() != null && !organizationDao.exists(entity.getClinic().getUri())) {
            organizationDao.persist(entity.getClinic());
        }
        super.update(entity);
    }
}
