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
package cz.cvut.kbss.jopa.example08.model;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;

import java.util.Date;


@OWLClass(iri = Vocabulary.C_REPORT)
public abstract class AbstractReport extends AbstractEntity {

    @OWLDataProperty(iri = Vocabulary.P_KEY, simpleLiteral = true)
    @ParticipationConstraints(nonEmpty = true)
    private String key;

    @OWLDataProperty(iri = Vocabulary.P_CREATED)
    @ParticipationConstraints(nonEmpty = true)
    private Date created;

    @OWLDataProperty(iri = Vocabulary.P_MODIFIED)
    private Date lastModified;

    @OWLDataProperty(iri = Vocabulary.P_DESCRIPTION)
    private String summary;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
