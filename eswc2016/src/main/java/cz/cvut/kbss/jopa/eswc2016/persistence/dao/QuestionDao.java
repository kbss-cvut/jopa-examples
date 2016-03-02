package cz.cvut.kbss.jopa.eswc2016.persistence.dao;

import cz.cvut.kbss.jopa.eswc2016.model.model.question;
import cz.cvut.kbss.jopa.eswc2016.util.KeyGenerator;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDao extends BaseDao<question> {

    public QuestionDao() {
        super(question.class);
    }

    @Override
    protected void persist(question entity, EntityManager em) {
        entity.setIdentifier(KeyGenerator.generateKey());
        super.persist(entity, em);
    }

    @Override
    protected void remove(question entity, EntityManager em) {
        throw new UnsupportedOperationException("Cannot remove an existing question.");
    }
}
