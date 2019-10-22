/**
 * Copyright (C) 2019 Czech Technical University in Prague
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.jopa.jsonld.service.repository;

import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.jsonld.model.Vocabulary;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.BaseDao;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.UserDao;
import cz.cvut.kbss.jopa.jsonld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Date;

@Service
public class UserRepositoryService extends BaseRepositoryService<User> implements UserService {

    private static final URI DEFAULT_USER_URI = URI
            .create(Vocabulary.ONTOLOGY_IRI_study_manager + "/System+Administrator");

    private final PlatformTransactionManager txManager;

    private final UserDao userDao;

    private User defaultUser;

    @Autowired
    public UserRepositoryService(UserDao userDao, PlatformTransactionManager txManager) {
        this.userDao = userDao;
        this.txManager = txManager;
    }

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
    public void initDefaultUser() {
        this.defaultUser = new TransactionTemplate(txManager).execute(transactionStatus -> {
            final User user;
            if (!userDao.exists(DEFAULT_USER_URI)) {
                user = new User();
                user.setUri(DEFAULT_USER_URI);
                user.setFirstName("System");
                user.setLastName("Administrator");
                userDao.persist(user);
            } else {
                user = userDao.find(DEFAULT_USER_URI);
            }
            return user;
        });
    }
}
