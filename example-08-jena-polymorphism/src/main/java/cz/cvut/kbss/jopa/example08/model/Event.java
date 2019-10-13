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
