package cz.cvut.kbss.jopa.eswc2016.service;

import cz.cvut.kbss.jopa.eswc2016.model.dto.ReportDto;
import cz.cvut.kbss.jopa.eswc2016.model.model.Report;
import cz.cvut.kbss.jopa.eswc2016.persistence.dao.BaseDao;
import cz.cvut.kbss.jopa.eswc2016.persistence.dao.PersonDao;
import cz.cvut.kbss.jopa.eswc2016.persistence.dao.ReportDao;
import cz.cvut.kbss.jopa.eswc2016.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ReportService extends BaseRepositoryService<Report> {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private PropertiesValidationService propertiesValidation;

    @Override
    protected BaseDao<Report> getPrimaryDao() {
        return reportDao;
    }

    public Report findByKey(Long key) {
        Objects.requireNonNull(key);
        return reportDao.findByKey(key);
    }

    @Override
    public void persist(Report instance) {
        Objects.requireNonNull(instance);
        propertiesValidation.validate(instance.getProperties());
        instance.setCreated(new Date());
        // Normally, we would query the security context here. For the demo purposes, we are using a predefined user
        instance.setHasAuthor(personDao.findByUsername(Constants.USERNAME));
        super.persist(instance);
    }

    @Override
    public void update(Report instance) {
        Objects.requireNonNull(instance);
        propertiesValidation.validate(instance.getProperties());
        super.update(instance);
    }

    public List<ReportDto> findAllDtos() {
        return reportDao.findAllDtos();
    }
}
