package cz.cvut.kbss.jopa.example07.model;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;

import java.util.Date;

@OWLClass(iri = Vocabulary.c_OccurrenceReport)
public class OccurrenceReport extends Report {

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_date)
    private Date date;

    @OWLDataProperty(iri = Vocabulary.p_severity)
    private Integer severity;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }
}
