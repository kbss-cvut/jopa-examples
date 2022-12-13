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
@OWLClass(iri = Vocabulary.s_c_organization)
public class OrganizationDto extends AbstractEntity {

    @OWLAnnotationProperty(iri = RDFS.LABEL)
    private String name;

    @OWLDataProperty(iri = Vocabulary.s_p_mbox)
    private String emailAddress;

    @OWLDataProperty(iri = DC.Terms.CREATED)
    private Date dateCreated;

    @OWLObjectProperty(iri = Vocabulary.s_p_has_member)
    private Set<UserDto> members;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Set<UserDto> getMembers() {
        return members;
    }

    public void setMembers(Set<UserDto> members) {
        this.members = members;
    }
}
