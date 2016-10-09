package cz.cvut.kbss.jopa.jsonld.service.repository;

import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.BaseDao;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.OrganizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationRepositoryService extends BaseRepositoryService<Organization> {

    @Autowired
    private OrganizationDao organizationDao;

    @Override
    protected BaseDao<Organization> getPrimaryDao() {
        return organizationDao;
    }
}
