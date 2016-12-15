package cz.cvut.kbss.jopa.example07.model;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;

import java.util.Date;
import java.util.Set;

@OWLClass(iri = Vocabulary.c_AuditReport)
public class AuditReport extends Report {

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_date)
    private Date date;

    @OWLDataProperty(iri = Vocabulary.p_hasFinding)
    private Set<String> findings;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<String> getFindings() {
        return findings;
    }

    public void setFindings(Set<String> findings) {
        this.findings = findings;
    }
}
