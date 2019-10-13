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
package cz.cvut.kbss.jopa.example01.model;

import cz.cvut.kbss.jopa.model.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Set;

@OWLClass(iri = "http://uob.iodt.ibm.com/univ-bench-dl.owl#UndergraduateStudent")
public class UndergraduateStudent {

    private static final Logger LOG = LoggerFactory.getLogger(UndergraduateStudent.class);

    @Id
    private URI uri;

    @OWLDataProperty(iri = "http://uob.iodt.ibm.com/univ-bench-dl.owl#firstName")
    private String firstName;

    @OWLDataProperty(iri = "http://uob.iodt.ibm.com/univ-bench-dl.owl#lastName")
    private String lastName;

    @OWLDataProperty(iri = "http://uob.iodt.ibm.com/univ-bench-dl.owl#emailAddress")
    private String email;

    @OWLDataProperty(iri = "http://uob.iodt.ibm.com/univ-bench-dl.owl#telephone")
    private String telephone;

    @OWLObjectProperty(iri = "http://uob.iodt.ibm.com/univ-bench-dl.owl#takesCourse", fetch = FetchType.LAZY)
    private Set<Course> courses;

    @OWLObjectProperty(iri = "http://uob.iodt.ibm.com/univ-bench-dl.owl#isAuthorOf", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<ConferencePaper> papers;

    @Types
    private Set<String> types;

    public UndergraduateStudent() {
    }

    public UndergraduateStudent(URI uri) {
        this.uri = uri;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<ConferencePaper> getPapers() {
        return papers;
    }

    public void setPapers(Set<ConferencePaper> papers) {
        this.papers = papers;
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    @PrePersist
    public void prePersist() {
        LOG.info("prePersist called.");
    }

    @PostPersist
    public void postPersist() {
        LOG.info("postPersist called.");
    }

    @PreUpdate
    public void preUpdate() {
        LOG.info("preUpdate called.");
    }

    @PostUpdate
    private void postUpdate() {
        LOG.info("postUpdate called. It can be private.");
    }

    @PostLoad
    void postLoad() {
        LOG.info("postLoad called. It can be package-private.");
    }

    @PreRemove
    protected void preRemove() {
        LOG.info("preRemove called. It can be protected.");
    }

    @Override
    public String toString() {
        return "UndergraduateStudent{" +
                "uri=" + uri +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", courses=" + courses +
                ", papers=" + papers +
                ", types=" + types +
                '}';
    }

    public static Field getCoursesField() {
        try {
            return UndergraduateStudent.class.getDeclaredField("courses");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
