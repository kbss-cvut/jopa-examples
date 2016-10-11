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

    @Autowired
    private StudyDao studyDao;

    @Autowired
    private UserService userService;

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
