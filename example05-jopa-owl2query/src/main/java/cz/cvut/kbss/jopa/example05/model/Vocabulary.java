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
package cz.cvut.kbss.jopa.example05.model;

/**
 * It is usually better to have OWL properties and classes defined in a vocabulary file, so that we can for example
 * reference them in queries (see {@link cz.cvut.kbss.jopa.example05.dao.SuperheroDao#findAllAssociates(cz.cvut.kbss.jopa.example05.model.Superhero)}).
 */
public class Vocabulary {

    private Vocabulary() {
        throw new AssertionError();
    }

    public static final String Superhero = "http://krizik.felk.cvut.cz/ontologies/jopa/example05#Superhero";

    public static final String p_firstName = "http://krizik.felk.cvut.cz/ontologies/jopa/example05#firstName";
    public static final String p_lastName = "http://krizik.felk.cvut.cz/ontologies/jopa/example05#lastName";
    public static final String p_nickname = "http://krizik.felk.cvut.cz/ontologies/jopa/example05#nickname";
    public static final String p_knows = "http://krizik.felk.cvut.cz/ontologies/jopa/example05#knows";

    public static final String p_goodGuy = "http://krizik.felk.cvut.cz/ontologies/jopa/example05#isGoodGuy";
}
