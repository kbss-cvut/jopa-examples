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
package cz.cvut.kbss.jopa.example08.service;

import cz.cvut.kbss.jopa.example08.model.*;
import cz.cvut.kbss.jopa.example08.persistence.SchemaExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class DataGenerator {

    private static final int REPORT_COUNT = 10;
    private static final int MAX_SEVERITY = 10;
    private static final String SUMMARY_BASE =
            "I am too lazy to generate report summary so I just reuse the same text and add a " +
                    "random number at the end. Here it is: ";

    private static String[] OCCURRENCE_TYPES = {
            "http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-430/v-1",
            "http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-430/v-10",
            "http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-430/v-100",
            "http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-430/v-101",
            "http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-430/v-102",
            "http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-430/v-103",
            "http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-430/v-104",
            "http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-430/v-105"
    };

    private static final String[] EVENT_TYPES = {
            "http://onto.fel.cvut.cz/ontologies/aviation-safety/Accident",
            "http://onto.fel.cvut.cz/ontologies/aviation-safety/Airborne_collision",
            "http://onto.fel.cvut.cz/ontologies/aviation-safety/Aircraft_collision",
            "http://onto.fel.cvut.cz/ontologies/aviation-safety/Bird_strike",
            "http://onto.fel.cvut.cz/ontologies/aviation-safety/Sabotage"
    };

    private static String[] AUDIT_TYPES = {
            "http://onto.fel.cvut.cz/ontologies/aviation/cz/caa/cat/audit/checklist/C01",
            "http://onto.fel.cvut.cz/ontologies/aviation/cz/caa/cat/audit/checklist/C02",
            "http://onto.fel.cvut.cz/ontologies/aviation/cz/caa/cat/audit/checklist/C03",
            "http://onto.fel.cvut.cz/ontologies/aviation/cz/caa/cat/audit/checklist/C04",
            "http://onto.fel.cvut.cz/ontologies/aviation/cz/caa/cat/audit/checklist/ramp-inspection"
    };


    private static final String[] FINDING_TYPES = {
            "http://onto.fel.cvut.cz/ontologies/aviation/cz/caa/cat/audit/checklist/C01/communication",
            "http://onto.fel.cvut.cz/ontologies/aviation/cz/caa/cat/audit/checklist/C01/crm",
            "http://onto.fel.cvut.cz/ontologies/aviation/cz/caa/cat/audit/checklist/C01/dangerous-goods",
            "http://onto.fel.cvut.cz/ontologies/aviation/cz/caa/cat/audit/checklist/C01/fire-and-smoke",
            "http://onto.fel.cvut.cz/ontologies/aviation/cz/caa/cat/audit/checklist/C01/survival-training"
    };

    private final ReportService reportService;
    private final SchemaExporter schemaExporter;

    private Random random;

    @Autowired
    public DataGenerator(ReportService reportService, SchemaExporter schemaExporter) {
        this.reportService = reportService;
        this.schemaExporter = schemaExporter;
        this.random = new Random();
    }

    @PostConstruct
    private void generate() {
        schemaExporter.exportSchema();
        generateOccurrenceReports(reportService);
        generateAuditReports(reportService);
    }

    private void generateOccurrenceReports(ReportService service) {
        final List<OccurrenceReport> reports = new ArrayList<>(REPORT_COUNT);
        for (int i = 0; i < REPORT_COUNT; i++) {
            final OccurrenceReport report = new OccurrenceReport();
            report.setSummary(SUMMARY_BASE + random.nextInt());
            final Occurrence occurrence = generateOccurrence(i);
            report.setOccurrence(occurrence);
            reports.add(report);
        }
        service.persist(reports);
    }

    private Occurrence generateOccurrence(int index) {
        final Occurrence occurrence = new Occurrence();
        occurrence.setName("Occurrence " + index);
        occurrence.setSeverity(random.nextInt(MAX_SEVERITY));
        occurrence.setStart(new Date(System.currentTimeMillis() - 10000));
        occurrence.setEnd(new Date());
        occurrence.setTypes(Collections.singleton(OCCURRENCE_TYPES[random.nextInt(OCCURRENCE_TYPES.length)]));
        for (int i = 0; i < REPORT_COUNT / 2; i++) {
            occurrence.addPart(generateEvent(occurrence));
        }
        return occurrence;
    }

    private Event generateEvent(Event parent) {
        final Event event = new Event();
        event.setStart(parent.getStart());
        event.setEnd(parent.getEnd());
        event.setTypes(Collections.singleton(EVENT_TYPES[random.nextInt(EVENT_TYPES.length)]));
        return event;
    }

    private void generateAuditReports(ReportService service) {
        final List<AuditReport> reports = new ArrayList<>(REPORT_COUNT);
        for (int i = 0; i < REPORT_COUNT; i++) {
            final AuditReport report = new AuditReport();
            report.setSummary(SUMMARY_BASE + random.nextInt());
            final Audit audit = generateAudit();
            report.setAudit(audit);
            reports.add(report);
        }
        service.persist(reports);
    }

    private Audit generateAudit() {
        final Audit audit = new Audit();
        audit.setStart(new Date(System.currentTimeMillis() - 24 * 3600 * 1000));
        audit.setEnd(new Date());
        audit.setTypes(Collections.singleton(AUDIT_TYPES[random.nextInt(AUDIT_TYPES.length)]));
        for (int i = 0; i < REPORT_COUNT / 2; i++) {
            audit.addFinding(generateFinding());
        }
        return audit;
    }

    private AuditFinding generateFinding() {
        final AuditFinding finding = new AuditFinding();
        finding.setSeverity(random.nextInt(MAX_SEVERITY));
        finding.setDescription(
                "Audit finding represents an issue discovered during audit. This one has severity " + finding
                        .getSeverity());
        finding.setTypes(Collections.singleton(FINDING_TYPES[random.nextInt(FINDING_TYPES.length)]));
        return finding;
    }
}
