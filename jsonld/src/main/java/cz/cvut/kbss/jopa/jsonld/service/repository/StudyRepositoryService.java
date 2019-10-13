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
package cz.cvut.kbss.jopa.jsonld.service.repository;

import cz.cvut.kbss.jopa.jsonld.model.Study;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.BaseDao;
import cz.cvut.kbss.jopa.jsonld.persistence.dao.StudyDao;
import cz.cvut.kbss.jopa.jsonld.service.StudyService;
import cz.cvut.kbss.jopa.jsonld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudyRepositoryService extends BaseRepositoryService<Study> implements StudyService {

    private final StudyDao studyDao;

    private final UserService userService;

    @Autowired
    public StudyRepositoryService(StudyDao studyDao, UserService userService) {
        this.studyDao = studyDao;
        this.userService = userService;
    }

    @Override
    protected BaseDao<Study> getPrimaryDao() {
        return studyDao;
    }

    @Override
    protected void prePersist(Study instance) {
        instance.setCreated(new Date());
        if (instance.getAuthor() == null) {
            instance.setAuthor(userService.getDefaultUser());
        }
    }

    @Override
    protected void preUpdate(Study instance) {
        final Study original = studyDao.find(instance.getUri());
        if (!original.getAuthor().equals(instance.getAuthor())) {
            throw new IllegalArgumentException("Study author cannot be changed.");
        }
        instance.setLastModified(new Date());
    }
}
