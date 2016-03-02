package cz.cvut.kbss.jopa.eswc2016.util;

import java.util.Objects;

public enum ConfigParam {

    REPOSITORY_TYPE("repositoryType");

    private final String name;

    ConfigParam(String name) {
        this.name = name;
    }

    public static ConfigParam fromString(String str) {
        Objects.requireNonNull(str);
        for (ConfigParam param : values()) {
            if (str.equalsIgnoreCase(param.name)) {
                return param;
            }
        }
        throw new IllegalArgumentException("Unsupported configuration parameter " + str);
    }
}
