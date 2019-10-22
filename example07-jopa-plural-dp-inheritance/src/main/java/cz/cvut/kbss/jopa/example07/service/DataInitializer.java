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

import cz.cvut.kbss.jopa.example07.model.AuditReport;
import cz.cvut.kbss.jopa.example07.model.OccurrenceReport;
import cz.cvut.kbss.jopa.example07.model.Report;
import cz.cvut.kbss.jopa.example07.model.SafetyIssueReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class DataInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);

    /**
     * Number of reports of each type.
     */
    private static final int REPORT_COUNT = 10;

    private final Random random = new Random();

    private final ReportService reportService;

    @Autowired
    public DataInitializer(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostConstruct
    public void initData() {
        if (!reportService.findAll().isEmpty()) {
            LOG.info("Sample data already exist.");
            return;
        }
        LOG.info("Generating sample data...");
        final List<Report> data = generateReports();
        reportService.persist(data);
        LOG.info("Sample data successfully persisted.");
    }

    private List<Report> generateReports() {
        final List<Report> data = new ArrayList<>();
        data.addAll(generateOccurrenceReports());
        data.addAll(generateAuditReports());
        data.addAll(generateSafetyIssueReports(data));
        return data;
    }

    private List<OccurrenceReport> generateOccurrenceReports() {
        final List<OccurrenceReport> list = new ArrayList<>();
        for (int i = 0; i < REPORT_COUNT; i++) {
            final OccurrenceReport r = new OccurrenceReport();
            setCommonAttributes(r, i);
            r.setSeverity(random.nextInt(10));
            r.setDate(new Date());
            list.add(r);
        }
        return list;
    }

    private void setCommonAttributes(Report report, int index) {
        report.setName(report.getClass().getSimpleName() + "_" + index);
        report.setSummary("A shocking summary of " + report.getName() + ".");
    }

    private List<AuditReport> generateAuditReports() {
        final List<AuditReport> list = new ArrayList<>();
        for (int i = 0; i < REPORT_COUNT; i++) {
            final AuditReport r = new AuditReport();
            setCommonAttributes(r, i);
            r.setDate(new Date(System.currentTimeMillis() - 24 * 3600 * 1000));
            r.setFindings(Stream.generate(() -> "Audit finding XYZ with result " + random.nextInt())
                                .limit(random.nextInt(REPORT_COUNT)).collect(Collectors.toSet()));
            list.add(r);
        }
        return list;
    }

    private List<SafetyIssueReport> generateSafetyIssueReports(List<Report> reports) {
        final List<SafetyIssueReport> list = new ArrayList<>();
        for (int i = 0; i < REPORT_COUNT; i++) {
            final SafetyIssueReport r = new SafetyIssueReport();
            setCommonAttributes(r, i);
            r.setBases(IntStream.range(0, random.nextInt(REPORT_COUNT)).boxed()
                                .map(k -> reports.get(random.nextInt(reports.size()))).collect(Collectors.toSet()));
            list.add(r);
        }
        return list;
    }
}
