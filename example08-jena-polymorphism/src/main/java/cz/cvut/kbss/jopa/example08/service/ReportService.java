package cz.cvut.kbss.jopa.example08.service;

import cz.cvut.kbss.jopa.example08.model.AbstractReport;
import cz.cvut.kbss.jopa.example08.persistence.dao.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.*;

@Service
public class ReportService {

    private final ReportDao reportDao;

    @Autowired
    public ReportService(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @Transactional
    public void persist(AbstractReport report) {
        Objects.requireNonNull(report);
        prePersist(report);
        reportDao.persist(report);
    }

    private void prePersist(AbstractReport report) {
        report.setCreated(new Date());
    }

    @Transactional
    public void persist(Collection<? extends AbstractReport> reports) {
        Objects.requireNonNull(reports);
        reports.forEach(r -> {
            prePersist(r);
            reportDao.persist(r);
        });
    }

    @Transactional(readOnly = true)
    public Optional<AbstractReport> find(URI uri) {
        Objects.requireNonNull(uri);
        return reportDao.find(uri);
    }

    @Transactional(readOnly = true)
    public Optional<AbstractReport> findByKey(String key) {
        Objects.requireNonNull(key);
        return reportDao.findByKey(key);
    }

    @Transactional(readOnly = true)
    public List<AbstractReport> findAll() {
        return reportDao.findAll();
    }

    @Transactional
    public void update(AbstractReport report) {
        Objects.requireNonNull(report);
        report.setLastModified(new Date());
        reportDao.update(report);
    }

    @Transactional
    public void remove(AbstractReport report) {
        Objects.requireNonNull(report);
        reportDao.remove(report);
    }
}
