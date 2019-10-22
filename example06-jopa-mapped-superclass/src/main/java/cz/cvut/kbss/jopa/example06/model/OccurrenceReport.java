/**
 * Copyright (C) 2019 Czech Technical University in Prague
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.jopa.example06.model;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;

import java.net.URI;
import java.util.Date;

@OWLClass(iri = Vocabulary.OccurrenceReport)
public class OccurrenceReport extends Report {

    @OWLDataProperty(iri = Vocabulary.startTime)
    private Date startTime;

    @OWLDataProperty(iri = Vocabulary.endTime)
    private Date endTime;

    @OWLObjectProperty(iri = Vocabulary.hasSeverity)
    private URI severity;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public URI getSeverity() {
        return severity;
    }

    public void setSeverity(URI severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "OccurrenceReport{" +
                "severity=" + severity +
                "} " + super.toString();
    }

    @Override
    public void printTableHeader() {
        System.out.println(String.format("%1$-177s", "").replace(' ', '-'));
        System.out.println(
                "|" + String.format("%1$-20s", "Headline") + "|" + String.format("%1$-30s", "Start time") + "|" +
                        String.format("%1$-30s", "End time") + "|" + String.format("%1$-75s", "Severity") + "|" +
                        String.format("%1$16s", "Author") + "|");
        System.out.println(String.format("%1$-177s", "").replace(' ', '-'));
    }

    @Override
    public void printTableRow() {
        System.out.println("|" + String.format("%1$-20s",
                getHeadline()) + "|" + String.format("%1$-30s", startTime) + "|" + String.format("%1$-30s", endTime) +
                        "|" + String.format("%1$-75s", severity) + "|" + String.format("%1$16s", getAuthor()) + "|");
    }
}
