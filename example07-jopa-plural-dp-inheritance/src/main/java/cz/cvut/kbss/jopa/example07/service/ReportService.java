/**
 * Copyright (C) 2019 Czech Technical University in Prague
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
