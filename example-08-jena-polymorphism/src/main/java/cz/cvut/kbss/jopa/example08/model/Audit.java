package cz.cvut.kbss.jopa.example08.model;

import cz.cvut.kbss.jopa.model.annotations.CascadeType;
import cz.cvut.kbss.jopa.model.annotations.FetchType;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;

import java.util.HashSet;
import java.util.Set;

@OWLClass(iri = Vocabulary.C_AUDIT)
public class Audit extends Event {

    @OWLObjectProperty(iri = Vocabulary.P_HAS_FINDING, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<AuditFinding> findings;

    public void addFinding(AuditFinding finding) {
        if (findings == null) {
            this.findings = new HashSet<>();
        }
        findings.add(finding);
    }

    public Set<AuditFinding> getFindings() {
        return findings;
    }

    public void setFindings(Set<AuditFinding> findings) {
        this.findings = findings;
    }

    @Override
    public String toString() {
        return "Audit{" +
                "types=" + getTypes() +
                "findings=" + findings +
                '}';
    }
}
