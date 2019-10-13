package cz.cvut.kbss.jopa.example08.model;

import cz.cvut.kbss.jopa.model.annotations.CascadeType;
import cz.cvut.kbss.jopa.model.annotations.FetchType;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;

@OWLClass(iri = Vocabulary.C_AUDIT_REPORT)
public class AuditReport extends AbstractReport {

    @OWLObjectProperty(iri = Vocabulary.P_DOCUMENTS, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Audit audit;

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    @Override
    public String toString() {
        return "AuditReport{" +
                "audit=" + audit +
                '}';
    }
}
