package cz.cvut.kbss.jopa.example07.model;

import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.jopa.vocabulary.RDFS;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

@OWLClass(iri = Vocabulary.c_Report)
public abstract class Report extends AbstractEntity {

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_key)
    private Integer key;

    @ParticipationConstraints(nonEmpty = true)
    @OWLAnnotationProperty(iri = RDFS.LABEL)
    private String name;

    @OWLAnnotationProperty(iri = Vocabulary.p_comment)
    private String summary;

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.p_created)
    private Date created;

    @OWLDataProperty(iri = Vocabulary.p_lastModified)
    private Date lastModified;

    @Types
    private Set<String> types;

    public Report() {
        // Explicitly assign the report type, so that polymorphic loading in DAO can work
        this.types = Collections.singleton(Vocabulary.c_Report);
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "key=" + key +
                ", name='" + name + '\'' +
                '}';
    }
}
