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
package cz.cvut.kbss.jopa.example08.model;

import cz.cvut.kbss.jopa.model.annotations.*;

@OWLClass(iri = Vocabulary.C_OCCURRENCE_REPORT)
public class OccurrenceReport extends AbstractReport {

    @OWLObjectProperty(iri = Vocabulary.P_DOCUMENTS, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @ParticipationConstraints(nonEmpty = true)
    private Occurrence occurrence;

    public Occurrence getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Occurrence occurrence) {
        this.occurrence = occurrence;
    }

    @Override
    public String toString() {
        return "OccurrenceReport{" +
                "occurrence=" + occurrence +
                '}';
    }
}
