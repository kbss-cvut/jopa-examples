package cz.cvut.kbss.jopa.eswc2016.service;

import com.github.jsonldjava.utils.Obj;
import cz.cvut.kbss.jopa.eswc2016.model.dto.ReportDto;
import cz.cvut.kbss.jopa.eswc2016.model.model.report;
import cz.cvut.kbss.jopa.eswc2016.persistence.dao.BaseDao;
import cz.cvut.kbss.jopa.eswc2016.persistence.dao.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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

    @Override
    public void persist(report instance) {
        Objects.requireNonNull(instance);
        instance.setCreated(new Date());
        super.persist(instance);
    }

    public List<ReportDto> findAllDtos() {
        return reportDao.findAllDtos();
    }
}
