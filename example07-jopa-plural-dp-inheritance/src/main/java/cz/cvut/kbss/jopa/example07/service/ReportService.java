package cz.cvut.kbss.jopa.example07.service;

import cz.cvut.kbss.jopa.example07.model.Report;
import cz.cvut.kbss.jopa.example07.persistence.dao.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ReportService {

    private final ReportDao reportDao;

    @Autowired
    public ReportService(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public List<Report> findAll() {
        return reportDao.findAll();
    }

    public Report find(Integer key) {
        return reportDao.findByKey(key);
    }

    public void persist(Report report) {
        Objects.requireNonNull(report);
        report.setCreated(new Date());
        reportDao.persist(report);
    }

    void persist(Collection<Report> reports) {
        assert reports != null;
        final Date created = new Date();
        reports.forEach(r -> r.setCreated(created));
        reportDao.persist(reports);
    }

    public void update(Report report) {
        Objects.requireNonNull(report);
        report.setLastModified(new Date());
        reportDao.update(report);
    }

    public void remove(Integer key) {
        reportDao.remove(key);
    }
}
