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
package cz.cvut.kbss.jopa.eswc2016.persistence.dao;

import cz.cvut.kbss.jopa.eswc2016.model.Vocabulary;
import cz.cvut.kbss.jopa.eswc2016.model.model.Person;
import cz.cvut.kbss.jopa.eswc2016.util.Constants;
import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.descriptors.EntityDescriptor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@Repository
public class PersonDao extends BaseDao<Person> {

    /**
     * Descriptor for saving persons into a dedicated context.
     */
    public static final EntityDescriptor PERSON_DESCRIPTOR = new EntityDescriptor(Constants.PERSONS_CONTEXT);

    public PersonDao() {
        super(Person.class);
    }

    @Override
    protected void persist(Person entity, EntityManager em) {
        em.persist(entity, PERSON_DESCRIPTOR);
    }

    @Override
    protected void update(Person entity, EntityManager em) {
        em.merge(entity, PERSON_DESCRIPTOR);
    }

    @Override
    protected Person findByUri(String uri, EntityManager em) {
        return em.find(Person.class, uri, PERSON_DESCRIPTOR);
    }

    public Person findByUsername(String username) {
        Objects.requireNonNull(username);

        final EntityManager em = entityManager();
        try {
            return findByUsername(username, em);
        } finally {
            em.close();
        }
    }

    protected Person findByUsername(String username, EntityManager em) {
        try {
            return em.createNativeQuery("SELECT DISTINCT ?p WHERE { ?p ?hasUsername ?username .}", Person.class)
                     .setParameter("hasUsername", URI.create(Vocabulary.s_p_accountName))
                     .setParameter("username", username, Constants.LANGUAGE)
                     .setDescriptor(new EntityDescriptor(Constants.PERSONS_CONTEXT))
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Makes sure that the default user is present in all storages.
     */
    @PostConstruct
    protected void initDefaultPerson() {
        final List<EntityManagerFactory> emfs = getAllEntityManagerFactories();
        for (EntityManagerFactory emf : emfs) {
            final EntityManager em = emf.createEntityManager();
            try {
                Person defaultUser = findByUsername(Constants.USERNAME, em);
                if (defaultUser == null) {
                    defaultUser = new Person();
                    defaultUser.setAccountName(Constants.USERNAME);
                    defaultUser.setFirstName("John");
                    defaultUser.setLastName("117");
                    em.getTransaction().begin();
                    persist(defaultUser, em);
                    em.getTransaction().commit();
                }
            } finally {
                em.close();
            }
        }
    }
}
