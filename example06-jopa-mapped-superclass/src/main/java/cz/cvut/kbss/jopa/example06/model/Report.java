package cz.cvut.kbss.jopa.example06.model;

import cz.cvut.kbss.jopa.CommonVocabulary;
import cz.cvut.kbss.jopa.model.annotations.*;

@MappedSuperclass
public abstract class Report extends AbstractEntity {

    @ParticipationConstraints(nonEmpty = true)
    @OWLAnnotationProperty(iri = CommonVocabulary.RDFS_LABEL)
    private String headline;

    @ParticipationConstraints(nonEmpty = true)
    @OWLObjectProperty(iri = Vocabulary.hasAuthor, fetch = FetchType.EAGER)
    private Person author;

    @OWLAnnotationProperty(iri = CommonVocabulary.DC_DESCRIPTION)
    private String summary;

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Report{" +
                "headline='" + headline + '\'' +
                "}";
    }

    public abstract void printTableHeader();

    public abstract void printTableRow();
}
