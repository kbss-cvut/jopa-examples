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
package cz.cvut.kbss.jopa.example04.service;

import cz.cvut.kbss.jopa.example04.model.Student;
import cz.cvut.kbss.jopa.example04.persistence.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class StudentRepositoryService {

    private final StudentDao studentDao;

    @Autowired
    public StudentRepositoryService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Transactional
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Transactional
    public Student findByKey(String key) {
        if (key == null || key.isEmpty()) {
            return null;
        }
        return studentDao.findByKey(key);
    }

    @Transactional
    public void persist(Student student) {
        Objects.requireNonNull(student);
        student.setKey(Long.toString(System.currentTimeMillis()));
        student.generateUri();
        studentDao.persist(student);
    }

    @Transactional
    public void delete(Student student) {
        Objects.requireNonNull(student);
        studentDao.delete(student);
    }
}
