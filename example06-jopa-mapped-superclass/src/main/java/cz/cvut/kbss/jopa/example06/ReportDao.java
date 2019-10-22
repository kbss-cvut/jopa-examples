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
import cz.cvut.kbss.jopa.example06.model.Vocabulary;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.vocabulary.RDFS;

import java.net.URI;
import java.util.List;

class ReportDao {

    private final EntityManager entityManager;

    ReportDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    List<OccurrenceReport> findOccurrenceReports() {
        return findAll(OccurrenceReport.class, Vocabulary.OccurrenceReport);
    }

    private <T> List<T> findAll(Class<T> type, String typeUri) {
        return entityManager
                .createNativeQuery("SELECT ?x WHERE { ?x a ?type ; ?hasHeadline ?headline . } ORDER BY ?headline", type)
                .setParameter("type", URI.create(typeUri))
                .setParameter("hasHeadline", URI.create(RDFS.LABEL))
                .getResultList();
    }

    List<AuditReport> findAuditReports() {
        return findAll(AuditReport.class, Vocabulary.AuditReport);
    }

    /**
     * Finds occurrence reports by their author.
     *
     * @param author The author
     * @return List of matching reports
     */
    List<OccurrenceReport> findOccurrenceReports(Person author) {
        return findByAuthor(OccurrenceReport.class, Vocabulary.OccurrenceReport, author);
    }

    private <T> List<T> findByAuthor(Class<T> type, String typeUri, Person author) {
        return entityManager
                .createNativeQuery("SELECT ?x WHERE { ?x a ?type ; " +
                        "?hasHeadline ?headline ; " +
                        "?hasAuthor ?author . " +
                        "} ORDER BY ?headline", type)
                .setParameter("type", URI.create(typeUri))
                .setParameter("hasHeadline", URI.create(RDFS.LABEL))
                .setParameter("hasAuthor", URI.create(Vocabulary.hasAuthor))
                .setParameter("author", author.getUri())
                .getResultList();
    }

    /**
     * Finds audit reports by their author.
     *
     * @param author The author
     * @return List of matching reports
     */
    List<AuditReport> findAuditReports(Person author) {
        return findByAuthor(AuditReport.class, Vocabulary.AuditReport, author);
    }
}
