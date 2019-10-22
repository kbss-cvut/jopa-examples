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
package cz.cvut.kbss.jopa.example06;

import cz.cvut.kbss.jopa.example06.model.AuditReport;
import cz.cvut.kbss.jopa.example06.model.OccurrenceReport;
import cz.cvut.kbss.jopa.example06.model.Person;

import java.net.URI;
import java.util.*;

public class DataGenerator {

    private static final int MAX_COUNT = 20;
    private static final List<URI> SEVERITIES = Arrays.asList(
            URI.create("http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-431/v-100"),
            URI.create("http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-431/v-200"),
            URI.create("http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-431/v-300"),
            URI.create("http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-431/v-301"),
            URI.create("http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-431/v-302"),
            URI.create("http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-431/v-400"),
            URI.create("http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-431/v-500"),
            URI.create("http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-431/v-501"),
            URI.create("http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-431/v-502")
    );

    private static final List<URI> OUTCOMES = Arrays.asList(
            URI.create("http://onto.fel.cvut.cz/ontologies/arms/sira/accident-outcome/catastrophic"),
            URI.create("http://onto.fel.cvut.cz/ontologies/arms/sira/accident-outcome/major"),
            URI.create("http://onto.fel.cvut.cz/ontologies/arms/sira/accident-outcome/minor"),
            URI.create("http://onto.fel.cvut.cz/ontologies/arms/sira/accident-outcome/negligible")
    );

    private final Random rand = new Random();

    private List<Person> authors;
    private List<OccurrenceReport> occurrenceReports;
    private List<AuditReport> auditReports;

    public void generateData() {
        generateAuthors();
        generateOccurrenceReports();
        generateAuditReports();
    }

    private void generateAuthors() {
        this.authors = new ArrayList<>();
        final Person pOne = new Person("Sherlock", "Holmes");
        pOne.setUsername("holmes@example.org");
        authors.add(pOne);
        final Person pTwo = new Person("John", "Watson");
        pTwo.setUsername("watson@example.org");
        authors.add(pTwo);
    }

    private void generateOccurrenceReports() {
        this.occurrenceReports = new ArrayList<>();
        for (int i = 0, count = randomPositiveInt(); i < count; i++) {
            final OccurrenceReport r = new OccurrenceReport();
            r.setAuthor(authors.get(randomIndex(authors)));
            r.setHeadline("OccurrenceReport " + i);
            r.setStartTime(new Date());
            r.setEndTime(new Date());
            r.setSeverity(SEVERITIES.get(randomIndex(SEVERITIES)));
            occurrenceReports.add(r);
        }
    }

    private void generateAuditReports() {
        this.auditReports = new ArrayList<>();
        for (int i = 0, count = randomPositiveInt(); i < count; i++) {
            final AuditReport r = new AuditReport();
            r.setAuthor(authors.get(randomIndex(authors)));
            r.setHeadline("AuditReport " + i);
            r.setDate(new Date());
            r.setOutcome(OUTCOMES.get(randomIndex(OUTCOMES)));
            r.setSummary("The audit found issues and its outcome was determined to be of level " + r.getOutcome());
            auditReports.add(r);
        }
    }

    private int randomPositiveInt() {
        int res;
        do {
            res = rand.nextInt(MAX_COUNT);
        } while (res < 2);
        return res;
    }

    private int randomIndex(Collection<?> col) {
        int res;
        do {
            res = rand.nextInt(col.size());
        } while (res < 0);
        return res;
    }

    public List<Person> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    public List<OccurrenceReport> getOccurrenceReports() {
        return Collections.unmodifiableList(occurrenceReports);
    }

    public List<AuditReport> getAuditReports() {
        return Collections.unmodifiableList(auditReports);
    }
}
