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
package cz.cvut.kbss.jopa.example07.persistence.dao;

import cz.cvut.kbss.jopa.example07.model.Report;
import cz.cvut.kbss.jopa.example07.model.Vocabulary;
import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Component
public class ReportDao {

    private static final Random RANDOM = new Random();

    private final EntityManagerFactory emf;

    @Autowired
    public ReportDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Report> findAll() {
        final EntityManager em = entityManager();
        try {
            return em.createNativeQuery("SELECT ?x WHERE { " +
                    "?x a ?report ;" +
                    "?created ?date . }" +
                    "ORDER BY ?date", Report.class).setParameter("report", URI.create(Vocabulary.c_Report))
                     .setParameter("created", URI.create(Vocabulary.p_created)).getResultList();
        } finally {
            em.close();
        }
    }

    public Report findByKey(Integer key) {
        Objects.requireNonNull(key);
        final EntityManager em = entityManager();
        return findByKey(key, em);
    }

    private Report findByKey(Integer key, EntityManager em) {
        try {
            return em.createNativeQuery("SELECT ?x WHERE {" +
                    "?x ?hasKey ?key . }", Report.class).setParameter("hasKey", URI.create(Vocabulary.p_key))
                     .setParameter("key", key).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void persist(Report report) {
        Objects.requireNonNull(report);
        generateIdentifier(report);
        final EntityManager em = entityManager();
        try {
            em.getTransaction().begin();
            em.persist(report);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private void generateIdentifier(Report report) {
        report.setKey(RANDOM.nextInt(Integer.MAX_VALUE));   // Makes sure the value is positive
    }

    public void persist(Collection<Report> reports) {
        Objects.requireNonNull(reports);
        if (reports.isEmpty()) {
            return;
        }
        final EntityManager em = entityManager();
        try {
            em.getTransaction().begin();
            reports.forEach(r -> {
                generateIdentifier(r);
                em.persist(r);
            });
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void update(Report report) {
        Objects.requireNonNull(report);
        final EntityManager em = entityManager();
        try {
            em.getTransaction().begin();
            em.merge(report);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void remove(Integer key) {
        Objects.requireNonNull(key);
        final EntityManager em = entityManager();
        try {
            em.getTransaction();
            final Report toRemove = findByKey(key, em);
            if (toRemove != null) {
                em.remove(toRemove);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private EntityManager entityManager() {
        return emf.createEntityManager();
    }
}
