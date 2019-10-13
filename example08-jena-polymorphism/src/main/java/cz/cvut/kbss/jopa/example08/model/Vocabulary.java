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
package cz.cvut.kbss.jopa.example08.model;

public class Vocabulary {

    public static final String BASE = "http://onto.fel.cvut.cz/ontologies/jopa/example08/";

    public static final String C_REPORT = BASE + "report";
    public static final String C_OCCURRENCE_REPORT = BASE + "occurrence-report";
    public static final String C_AUDIT_REPORT = BASE + "audit-report";
    public static final String C_EVENT = "http://onto.fel.cvut.cz/ontologies/ufo/event";
    public static final String C_OCCURRENCE = BASE + "occurrence";
    public static final String C_AUDIT = BASE + "audit";
    public static final String C_AUDIT_FINDING = BASE + "audit-finding";

    public static final String P_CREATED = "http://purl.org/dc/terms/created";
    public static final String P_MODIFIED = "http://purl.org/dc/terms/modified";
    public static final String P_DESCRIPTION = "http://purl.org/dc/terms/description";
    public static final String P_START = BASE + "start";
    public static final String P_END = BASE + "end";
    public static final String P_HAS_PART = "http://onto.fel.cvut.cz/ontologies/ufo/has-part";
    public static final String P_HAS_FINDING = BASE + "has-finding";
    public static final String P_SEVERITY = BASE + "severity";
    public static final String P_DOCUMENTS = BASE + "documents";
    public static final String P_KEY = BASE + "key";
}
