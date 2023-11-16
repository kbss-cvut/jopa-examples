/*
 * JOPA Examples
 * Copyright (C) 2023 Czech Technical University in Prague
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package cz.cvut.kbss.jopa.jsonld.dto;

import cz.cvut.kbss.jopa.jsonld.model.AbstractEntity;
import cz.cvut.kbss.jopa.jsonld.model.Vocabulary;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLDataProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;
import cz.cvut.kbss.jopa.model.annotations.Types;
import cz.cvut.kbss.jopa.model.annotations.util.NonEntity;

import java.util.Date;
import java.util.Set;

@NonEntity
@OWLClass(iri = Vocabulary.s_c_user)
public class UserDto extends AbstractEntity {

    @OWLDataProperty(iri = Vocabulary.s_p_firstName)
    private String firstName;

    @OWLDataProperty(iri = Vocabulary.s_p_lastName)
    private String lastName;

    @OWLDataProperty(iri = Vocabulary.s_p_mbox)
    private String emailAddress;

    @OWLDataProperty(iri = Vocabulary.s_p_created)
    private Date dateCreated;

    @OWLObjectProperty(iri = Vocabulary.s_p_is_member_of)
    private OrganizationDto clinic;

    @Types
    private Set<String> types;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public OrganizationDto getClinic() {
        return clinic;
    }

    public void setClinic(OrganizationDto clinic) {
        this.clinic = clinic;
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }
}
