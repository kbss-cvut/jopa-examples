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
package cz.cvut.kbss.jopa.example07.model;

public class Vocabulary {

    public static final String c_Report = "http://onto.fel.cvut.cz/ontologies/documentation/report";
    public static final String c_OccurrenceReport = "http://onto.fel.cvut.cz/ontologies/documentation/occurrence_report";
    public static final String c_AuditReport = "http://onto.fel.cvut.cz/ontologies/documentation/audit_report";
    public static final String c_SafetyIssueReport = "http://onto.fel.cvut.cz/ontologies/documentation/safety_issue_report";

    public static final String p_key = "http://onto.fel.cvut.cz/ontologies/documentation/has_key";
    public static final String p_comment = "http://www.w3.org/2000/01/rdf-schema#comment";
    public static final String p_created = "http://purl.org/dc/terms/created";
    public static final String p_lastModified = "http://purl.org/dc/terms/modified";
    public static final String p_date = "http://purl.org/dc/terms/date";
    public static final String p_hasFinding = "http://onto.fel.cvut.cz/ontologies/documentation/has_finding";
    public static final String p_basedOn = "http://onto.fel.cvut.cz/ontologies/documentation/based_on";
    public static final String p_severity = "http://onto.fel.cvut.cz/ontologies/documentation/has_severity_assessment";

    private Vocabulary() {
        throw new AssertionError();
    }
}
