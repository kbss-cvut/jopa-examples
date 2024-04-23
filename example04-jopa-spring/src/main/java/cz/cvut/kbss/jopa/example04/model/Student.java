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
package cz.cvut.kbss.jopa.example04.model;

import cz.cvut.kbss.jopa.model.annotations.*;
import lombok.Data;

import java.io.Serializable;
import java.net.URI;

@Data
@NamedNativeQueries({
        @NamedNativeQuery(name = "Student.findAll", query = "SELECT ?x WHERE { ?x a <" + Vocabulary.Student + "> . }"),
        @NamedNativeQuery(name = "Student.findByKey", query = "SELECT ?x WHERE {?x <" + Vocabulary.p_key + "> ?key . }")
})
@OWLClass(iri = Vocabulary.Student)
public class Student implements Serializable {

    @Id
    private URI uri;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_key, simpleLiteral = true)
    private String key;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_firstName)
    private String firstName;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_lastName)
    private String lastName;

    @OWLDataProperty(iri = Vocabulary.p_emailAddress)
    private String email;

    public void generateUri() {
        if (uri != null) {
            return;
        }
        assert firstName != null;
        assert lastName != null;
        this.uri = URI.create(Vocabulary.URI_BASE + firstName + '+' + lastName);
    }
}
