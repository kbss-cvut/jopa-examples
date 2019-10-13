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
package cz.cvut.kbss.jopa.example08.persistence.dao;

import cz.cvut.kbss.jopa.example08.model.AbstractReport;
import cz.cvut.kbss.jopa.example08.model.Vocabulary;
import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class ReportDao {

    private final EntityManager em;

    private final Random random = new Random();

    ReportDao(EntityManager em) {
        this.em = em;
    }

    public void persist(AbstractReport instance) {
        instance.setKey(generateKey());
        em.persist(instance);
    }

    private String generateKey() {
        return Integer.toString(random.nextInt(Integer.MAX_VALUE));
    }

    public Optional<AbstractReport> find(URI id) {
        return Optional.ofNullable(em.find(AbstractReport.class, id));
    }

    public List<AbstractReport> findAll() {
        return em.createNativeQuery("SELECT ?x WHERE { ?x a ?type . }", AbstractReport.class)
                 .setParameter("type", URI.create(Vocabulary.C_REPORT)).getResultList();
    }

    public Optional<AbstractReport> findByKey(String key) {
        try {
            return Optional
                    .of(em.createNativeQuery("SELECT ?x WHERE { ?x a ?type ; ?hasKey ?key . }", AbstractReport.class)
                          .setParameter("type", URI.create(Vocabulary.C_REPORT))
                          .setParameter("hasKey", URI.create(Vocabulary.P_KEY))
                          .setParameter("key", key, "en").getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void update(AbstractReport instance) {
        em.merge(instance);
    }

    public void remove(AbstractReport instance) {
        find(instance.getUri()).ifPresent(em::remove);
    }
}
