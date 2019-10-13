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
