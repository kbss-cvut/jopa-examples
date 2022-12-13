package cz.cvut.kbss.jopa.jsonld.dto;

import cz.cvut.kbss.jopa.jsonld.model.AbstractEntity;
import cz.cvut.kbss.jopa.jsonld.model.Vocabulary;
import cz.cvut.kbss.jopa.model.annotations.OWLAnnotationProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import cz.cvut.kbss.jopa.model.annotations.util.NonEntity;
import cz.cvut.kbss.jopa.vocabulary.DC;
import cz.cvut.kbss.jopa.vocabulary.RDFS;

import java.util.Date;
import java.util.Set;

@NonEntity
@OWLClass(iri = Vocabulary.s_c_study)
public class StudyDto extends AbstractEntity {

    @OWLAnnotationProperty(iri = RDFS.LABEL)
    private String name;

    @OWLDataProperty(iri = DC.Terms.CREATED)
    private Date created;

    @OWLObjectProperty(iri = Vocabulary.s_p_has_author)
    private UserDto author;

    @OWLDataProperty(iri = Vocabulary.s_p_modified)
    private Date lastModified;

    @OWLObjectProperty(iri = Vocabulary.s_p_has_last_editor)
    private UserDto lastModifiedBy;

    @OWLObjectProperty(iri = Vocabulary.s_p_has_admin)
    private Set<UserDto> administrators;

    /**
     * Participating doctors.
     */
    @OWLObjectProperty(iri = Vocabulary.s_p_has_participant)
    private Set<UserDto> participants;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public UserDto getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(UserDto lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<UserDto> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(Set<UserDto> administrators) {
        this.administrators = administrators;
    }

    public Set<UserDto> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<UserDto> participants) {
        this.participants = participants;
    }
}
