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

import java.net.URI;

public abstract class Constants {

    private Constants() {
        throw new AssertionError();
    }

    /**
     * URI of context in which persons are stored.
     */
    public static final URI PERSONS_CONTEXT = URI.create("http://krizik.felk.cvut.cz/ontologies/eswc2016/persons");

    /**
     * Default user's username.
     */
    public static final String USERNAME = "master-chief";

    /**
     * Default ontology language.
     */
    public static final String LANGUAGE = "en";

    public static final String APPLICATION_JSON_LD_TYPE = "application/ld+json";

    public static final String UTF_8_ENCODING = "UTF-8";
}
