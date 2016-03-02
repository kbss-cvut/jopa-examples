package cz.cvut.kbss.jopa.eswc2016.util;

import java.util.Objects;

public enum RepositoryType {

    SESAME("sesame"), OWLAPI("owlapi");

    private final String name;

    RepositoryType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static RepositoryType fromString(String str) {
        Objects.requireNonNull(str);
        for (RepositoryType type : values()) {
            if (str.equalsIgnoreCase(type.name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported repository type " + str);
    }
}
