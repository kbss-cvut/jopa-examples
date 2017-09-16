package cz.cvut.kbss.jopa.jsonld.service.repository;

import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.BaseDao;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.OrganizationDao;
import cz.cvut.kbss.jopa.jsonld.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrganizationRepositoryService extends BaseRepositoryService<Organization> implements OrganizationService {

    private final OrganizationDao organizationDao;

    @Autowired
    public OrganizationRepositoryService(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    @Override
    protected BaseDao<Organization> getPrimaryDao() {
        return organizationDao;
    }

    @Override
    protected void prePersist(Organization instance) {
        instance.setDateCreated(new Date());
    }
}
