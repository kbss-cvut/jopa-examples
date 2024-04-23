/*
 * JOPA Examples
 * Copyright (C) 2024 Czech Technical University in Prague
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package cz.cvut.kbss.jopa.eswc2016.util;

import java.util.Objects;

public enum RepositoryType {

    RDF4J("rdf4j"), OWLAPI("owlapi");

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
