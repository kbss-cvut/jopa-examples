package cz.cvut.kbss.jopa.example09.model;

import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.jopa.vocabulary.RDFS;

import java.io.Serializable;
import java.net.URI;
import java.util.Set;

@OWLClass(iri = "http://dbpedia.org/class/yago/WikicatVideoGameDevelopmentCompanies")
public class Developer implements Serializable {

    @Id
    private URI uri;

    @ParticipationConstraints(nonEmpty = true)
    @OWLAnnotationProperty(iri = "foaf:name")
    private String name;

    @OWLAnnotationProperty(iri = RDFS.COMMENT)
    private String comment;

    @OWLAnnotationProperty(iri = "foaf:homepage")
    private Set<URI> homepage;

    @OWLDataProperty(iri = "dbo:numberOfEmployees")
    private Integer employeeCount;

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

    public Set<URI> getHomepage() {
        return homepage;
    }

    public void setHomepage(Set<URI> homepage) {
        this.homepage = homepage;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }
}
