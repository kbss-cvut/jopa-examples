package cz.cvut.kbss.jopa.eswc2016.service;

import cz.cvut.kbss.jopa.eswc2016.model.model.Question;
import cz.cvut.kbss.jopa.eswc2016.persistence.dao.BaseDao;
import cz.cvut.kbss.jopa.eswc2016.persistence.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class QuestionService extends BaseRepositoryService<Question> {

    @Autowired
    private QuestionDao questionDao;

    @Override
    protected BaseDao<Question> getPrimaryDao() {
        return questionDao;
    }

    public Question findByKey(Long key) {
        Objects.requireNonNull(key);
        return questionDao.findByKey(key);
    }

    @Override
    public void remove(Question instance) {
        throw new UnsupportedOperationException("Cannot remove a question.");
    }
}
