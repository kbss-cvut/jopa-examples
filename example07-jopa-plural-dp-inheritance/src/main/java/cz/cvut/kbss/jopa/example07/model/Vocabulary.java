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
