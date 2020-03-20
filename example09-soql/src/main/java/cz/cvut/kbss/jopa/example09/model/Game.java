package cz.cvut.kbss.jopa.example09.model;

import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.jopa.vocabulary.RDFS;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;

@OWLClass(iri = "dbo:VideoGame")
public class Game implements Serializable {

    @Id
    private URI uri;

    @ParticipationConstraints(nonEmpty = true)
    @OWLAnnotationProperty(iri = "foaf:name")
    private String name;

    @OWLAnnotationProperty(iri = RDFS.COMMENT)
    private String comment;

    @OWLObjectProperty(iri = "dbo:developer", fetch = FetchType.EAGER)
    private Developer developer;

    @OWLDataProperty(iri = "dbo:releaseDate")
    private LocalDate releaseDate;

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
