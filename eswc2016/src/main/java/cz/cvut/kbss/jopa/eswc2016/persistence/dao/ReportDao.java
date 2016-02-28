package cz.cvut.kbss.jopa.eswc2016.persistence.dao;

import cz.cvut.kbss.jopa.eswc2016.model.model.report;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDao extends BaseDao<report> {

    public ReportDao() {
        super(report.class);
    }
}
