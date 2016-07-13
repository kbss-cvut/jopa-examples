/**
 * Copyright (C) 2016 Czech Technical University in Prague
 * <p>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.jopa.example04.persistence.dao;

import cz.cvut.kbss.jopa.example04.model.Student;
import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDao {

    private static final Logger LOG = LoggerFactory.getLogger(StudentDao.class);

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager entityManager() {
        return emf.createEntityManager();
    }

    public List<Student> findAll() {
        final EntityManager em = entityManager();
        try {
            return em.createNamedQuery("Student.findAll", Student.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Student findByKey(String key) {
        final EntityManager em = entityManager();
        try {
            return em.createNamedQuery("Student.findByKey", Student.class).setParameter("key", key, "en")
                     .getSingleResult();
        } catch (NoResultException e) {
            LOG.warn("Student with key {} not found.", key);
            return null;
        } finally {
            em.close();
        }
    }

    public void persist(Student student) {
        assert student != null;
        assert student.getUri() != null;

        final EntityManager em = entityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        LOG.debug("Student {} persisted.", student);
    }

    public void delete(Student student) {
        assert student != null;

        final EntityManager em = entityManager();
        try {
            em.getTransaction().begin();
            final Student toRemove = em.merge(student);
            em.remove(toRemove);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        LOG.debug("Student {} deleted.", student);
    }
}
