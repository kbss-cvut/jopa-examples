package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.jsonld.model.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationDao extends DerivableUriDao<Organization> {

    public OrganizationDao() {
        super(Organization.class);
    }
}
