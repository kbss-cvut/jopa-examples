package cz.cvut.kbss.jopa.example08.model;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;
import cz.cvut.kbss.jopa.model.annotations.Types;

import java.util.Set;

@OWLClass(iri = Vocabulary.C_AUDIT_FINDING)
public class AuditFinding extends AbstractEntity {

    @OWLDataProperty(iri = Vocabulary.P_SEVERITY)
    @ParticipationConstraints(nonEmpty = true)
    private Integer severity;

    @OWLDataProperty(iri = Vocabulary.P_DESCRIPTION)
    private String description;

    @Types
    private Set<String> types;

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "AuditFinding{" +
                "severity=" + severity +
                ", types=" + types +
                '}';
    }
}
