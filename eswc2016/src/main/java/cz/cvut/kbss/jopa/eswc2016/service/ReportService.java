package cz.cvut.kbss.jopa.eswc2016.service;

import cz.cvut.kbss.jopa.eswc2016.model.model.report;
import cz.cvut.kbss.jopa.eswc2016.persistence.dao.BaseDao;
import cz.cvut.kbss.jopa.eswc2016.persistence.dao.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ReportService extends BaseRepositoryService<report> {

    @Autowired
    private ReportDao reportDao;

    @Override
    protected BaseDao<report> getPrimaryDao() {
        return reportDao;
    }

    public report findByKey(Long key) {
        Objects.requireNonNull(key);
        return reportDao.findByKey(key);
    }
}
