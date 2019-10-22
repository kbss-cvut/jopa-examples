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

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@OWLClass(iri = Vocabulary.C_EVENT)
public class Event extends AbstractEntity {

    @OWLDataProperty(iri = Vocabulary.P_START)
    @ParticipationConstraints(nonEmpty = true)
    private Date start;

    @OWLDataProperty(iri = Vocabulary.P_END)
    @ParticipationConstraints(nonEmpty = true)
    private Date end;

    @OWLObjectProperty(iri = Vocabulary.P_HAS_PART, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Event> parts;

    @Types
    private Set<String> types;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Set<Event> getParts() {
        return parts;
    }

    public void setParts(Set<Event> parts) {
        this.parts = parts;
    }

    public void addPart(Event part) {
        Objects.requireNonNull(part);
        if (parts == null) {
            this.parts = new HashSet<>();
        }
        parts.add(part);
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }
}
