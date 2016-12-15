package cz.cvut.kbss.jopa.example07.model;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;

import java.util.Set;

@OWLClass(iri = Vocabulary.c_SafetyIssueReport)
public class SafetyIssueReport extends Report {

    @OWLObjectProperty(iri = Vocabulary.p_basedOn)
    private Set<Report> bases;

    public Set<Report> getBases() {
        return bases;
    }

    public void setBases(Set<Report> bases) {
        this.bases = bases;
    }
}
