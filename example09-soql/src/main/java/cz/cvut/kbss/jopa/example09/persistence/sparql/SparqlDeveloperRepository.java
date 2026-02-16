package cz.cvut.kbss.jopa.example09.persistence.sparql;

import cz.cvut.kbss.jopa.example09.model.Developer;
import cz.cvut.kbss.jopa.example09.persistence.DeveloperRepository;
import cz.cvut.kbss.jopa.example09.persistence.QueryMechanism;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Repository
public class SparqlDeveloperRepository implements DeveloperRepository {

    private final EntityManager em;

    public SparqlDeveloperRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Developer> findAll() {
        return em.createNativeQuery("""
                                            PREFIX foaf: <http://xmlns.com/foaf/0.1/>
                                            SELECT ?d WHERE {
                                              ?d a <http://dbpedia.org/class/yago/WikicatVideoGameDevelopmentCompanies> ;
                                                 foaf:name ?name .
                                            } ORDER BY ?name""", Developer.class).getResultList();
    }

    @Override
    public List<Developer> findByName(String name) {
        return em.createNativeQuery("""
                                            PREFIX foaf: <http://xmlns.com/foaf/0.1/>
                                            SELECT ?d WHERE {
                                              ?d a <http://dbpedia.org/class/yago/WikicatVideoGameDevelopmentCompanies> ;
                                                 foaf:name ?name .
                                              FILTER (regex(?name, ?nameParam, "i"))
                                            } ORDER BY ?name""", Developer.class)
                 .setParameter("nameParam", name)
                 .getResultList();
    }

    @Override
    public List<Developer> findSmallDevelopers() {
        return em.createNativeQuery("""
                                            PREFIX foaf: <http://xmlns.com/foaf/0.1/>
                                            PREFIX dbo: <http://dbpedia.org/ontology/>
                                            SELECT ?d WHERE {
                                              ?d a <http://dbpedia.org/class/yago/WikicatVideoGameDevelopmentCompanies> ;
                                                 foaf:name ?name ;
                                                 dbo:numberOfEmployees ?employeeCount .
                                                 FILTER (?employeeCount < ?smallDeveloperThreshold)
                                            } ORDER BY ?name""", Developer.class)
                 .setParameter("smallDeveloperThreshold", DeveloperRepository.SMALL_DEVELOPER_SIZE)
                 .getResultList();
    }

    @Override
    public Optional<Developer> findById(String localName) {
        return Optional.ofNullable(em.find(Developer.class, URI.create(NAMESPACE + localName)));
    }

    @Override
    public QueryMechanism getQueryMechanism() {
        return QueryMechanism.SPARQL;
    }
}
