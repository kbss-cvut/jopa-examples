package cz.cvut.kbss.jopa.jsonld.service;

import cz.cvut.kbss.jopa.jsonld.model.User;

public interface UserService extends BaseService<User> {

    /**
     * Gets default system user.
     * <p>
     * This would normally return the currently logged in user. But for demo purposes, we create a default user.
     *
     * @return Default user
     */
    User getDefaultUser();
}
