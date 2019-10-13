package cz.cvut.kbss.jopa.example08.model;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.ParticipationConstraints;

import java.util.Date;


@OWLClass(iri = Vocabulary.C_REPORT)
public abstract class AbstractReport extends AbstractEntity {

    @OWLDataProperty(iri = Vocabulary.P_KEY)
    @ParticipationConstraints(nonEmpty = true)
    private String key;

    @OWLDataProperty(iri = Vocabulary.P_CREATED)
    @ParticipationConstraints(nonEmpty = true)
    private Date created;

    @OWLDataProperty(iri = Vocabulary.P_MODIFIED)
    private Date lastModified;

    @OWLDataProperty(iri = Vocabulary.P_DESCRIPTION)
    private String summary;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
