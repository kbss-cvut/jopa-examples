package cz.cvut.kbss.jopa.example06.model;

public class Vocabulary {

    private Vocabulary() {
        throw new AssertionError();
    }

    static final String URI_BASE = "http://krizik.felk.cvut.cz/ontologies/example06/";

    public static final String Person = URI_BASE + "Person";
    public static final String OccurrenceReport = URI_BASE + "OccurrenceReport";
    public static final String AuditReport = URI_BASE + "AuditReport";

    static final String firstName = URI_BASE + "firstName";
    static final String lastName = URI_BASE + "lastName";
    static final String username = URI_BASE + "username";
    public static final String hasAuthor = URI_BASE + "hasAuthor";
    static final String startTime = URI_BASE + "startTime";
    static final String endTime = URI_BASE + "endTime";
    static final String date = "http://purl.org/dc/terms/date";
    static final String hasSeverity = URI_BASE + "hasSeverity";
    static final String hasOutcome = URI_BASE + "hasOutcome";
}
