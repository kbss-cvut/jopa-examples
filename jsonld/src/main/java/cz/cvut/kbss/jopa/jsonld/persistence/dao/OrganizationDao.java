package cz.cvut.kbss.jopa.jsonld.persistence.dao;

import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.jsonld.model.Vocabulary;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class OrganizationDao extends DerivableUriDao<Organization> {

    public OrganizationDao() {
        super(Organization.class);
    }

    @Override
    void persist(Organization entity, EntityManager em) {
        entity.setKey(Long.toString(System.currentTimeMillis()));
        super.persist(entity, em);
    }

    public Organization findByKey(String key) {
        final EntityManager em = entityManager();
        try {
            return em.createNativeQuery("SELECT ?x WHERE { ?x a ?type ; ?hasKey ?key . }", Organization.class)
                     .setParameter("type", typeUri).setParameter("hasKey",
                            URI.create(Vocabulary.s_p_key)).setParameter("key", key, "en").getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
