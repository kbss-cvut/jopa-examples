package cz.cvut.kbss.jopa.example09.persistence;

public enum QueryMechanism {
    SOQL, CRITERIA, SPARQL, SPARQL_MAPPING;

    public static QueryMechanism fromString(String name) {
        if (name == null) {
            return SOQL;
        }
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return SOQL;
        }
    }
}
