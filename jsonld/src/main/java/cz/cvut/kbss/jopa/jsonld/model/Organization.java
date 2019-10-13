/**
 * Copyright (C) 2019 Czech Technical University in Prague
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.jopa.jsonld.model;

import cz.cvut.kbss.jopa.jsonld.model.util.HasDerivableUri;
import cz.cvut.kbss.jopa.model.annotations.*;
import cz.cvut.kbss.jopa.vocabulary.DC;
import cz.cvut.kbss.jopa.vocabulary.RDFS;

import java.net.URI;
import java.util.Date;
import java.util.Set;

@OWLClass(iri = Vocabulary.s_c_organization)
public class Organization extends AbstractEntity implements HasDerivableUri {

    @ParticipationConstraints(nonEmpty = true)
    @OWLAnnotationProperty(iri = RDFS.LABEL)
    private String name;

    @OWLDataProperty(iri = Vocabulary.s_p_mbox)
    private String emailAddress;

    @OWLDataProperty(iri = DC.Terms.CREATED)
    private Date dateCreated;

    @Inferred
    @OWLObjectProperty(iri = Vocabulary.s_p_has_member)
    private Set<User> members;

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

        return getKey() != null ? getKey().equals(that.getKey()) : that.getKey() == null;

    }

    @Override
    public int hashCode() {
        return getKey() != null ? getKey().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                "} ";
    }
}
