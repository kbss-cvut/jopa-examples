package cz.cvut.kbss.jopa.eswc2016.persistence.dao;

import cz.cvut.kbss.jopa.eswc2016.model.model.report;
import cz.cvut.kbss.jopa.eswc2016.util.KeyGenerator;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDao extends BaseDao<report> {

    public ReportDao() {
        super(report.class);
    }

    @Override
    protected void persist(report entity, EntityManager em) {
        entity.setIdentifier(KeyGenerator.generateKey());
        super.persist(entity, em);
    }
}
