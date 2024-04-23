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
package cz.cvut.kbss.jopa.example06.model;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;

import java.net.URI;
import java.util.Date;

@OWLClass(iri = Vocabulary.AuditReport)
public class AuditReport extends Report {

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.date)
    private Date date;

    @ParticipationConstraints(nonEmpty = true)
    @OWLObjectProperty(iri = Vocabulary.hasOutcome)
    private URI outcome;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public URI getOutcome() {
        return outcome;
    }

    public void setOutcome(URI outcome) {
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return "AuditReport{" +
                "date=" + date +
                ", outcome=" + outcome +
                "} " + super.toString();
    }

    @Override
    public void printTableHeader() {
        System.out.println(String.format("%1$-146s", "").replace(' ', '-'));
        System.out.println(
                "|" + String.format("%1$-20s", "Headline") + "|" + String.format("%1$-30s", "Date") + "|" +
                        String.format("%1$-75s", "Outcome") + "|" +
                        String.format("%1$16s", "Author") + "|");
        System.out.println(String.format("%1$-146s", "").replace(' ', '-'));
    }

    @Override
    public void printTableRow() {
        System.out.println("|" + String.format("%1$-20s",
                getHeadline()) + "|" + String.format("%1$-30s", date) + "|" + String.format("%1$-75s", outcome) + "|" +
                        String.format("%1$16s", getAuthor()) + "|");
    }
}
