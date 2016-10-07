package cz.cvut.kbss.jopa.jsonld.model;

import cz.cvut.kbss.jopa.jsonld.model.util.HasDerivableUri;
import cz.cvut.kbss.jopa.model.annotations.*;

import java.net.URI;
import java.util.Date;
import java.util.Set;

@OWLClass(iri = Vocabulary.s_c_organization)
public class Organization extends AbstractEntity implements HasDerivableUri {

    @ParticipationConstraints(nonEmpty = true)
    @OWLDataProperty(iri = Vocabulary.s_p_key)
    private String key;

    @ParticipationConstraints(nonEmpty = true)
    @OWLAnnotationProperty(iri = Vocabulary.s_p_label)
    private String name;

    @OWLDataProperty(iri = Vocabulary.s_p_mbox)
    private String emailAddress;

    @OWLDataProperty(iri = Vocabulary.s_p_created)
    private Date dateCreated;

    @Inferred
    @OWLObjectProperty(iri = Vocabulary.s_p_has_member, readOnly = true)
    private Set<User> members;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    /**
     * Gets date when this clinic's account was created.
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    @Override
    public void generateUri() {
        if (getUri() != null) {
            return;
        }
        if (name == null) {
            throw new IllegalStateException("Cannot generate URI for organization without a name.");
        }
        this.setUri(URI.create(Vocabulary.ONTOLOGY_IRI_study_manager + "/" + name));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        return key != null ? key.equals(that.key) : that.key == null;

    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Clinic{" +
                "name='" + name + '\'' +
                "} ";
    }
}
