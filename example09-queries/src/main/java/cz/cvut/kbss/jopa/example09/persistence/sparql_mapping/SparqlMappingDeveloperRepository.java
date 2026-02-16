package cz.cvut.kbss.jopa.example09.persistence.sparql_mapping;

import cz.cvut.kbss.jopa.example09.model.Developer;
import cz.cvut.kbss.jopa.example09.persistence.DeveloperRepository;
import cz.cvut.kbss.jopa.example09.persistence.QueryMechanism;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Repository
public class SparqlMappingDeveloperRepository implements DeveloperRepository {

    private final EntityManager em;

    public SparqlMappingDeveloperRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Developer> findAll() {
        return em.createNativeQuery("""
                                            PREFIX foaf: <http://xmlns.com/foaf/0.1/>
                                            PREFIX dbo: <http://dbpedia.org/ontology/>
                                            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                                            SELECT ?d ?name ?comment ?homepage ?employeeCount ?types WHERE {
                                              ?d a <http://dbpedia.org/class/yago/WikicatVideoGameDevelopmentCompanies> ;
                                                 foaf:name ?name ;
                                                 a ?types .
                                                 OPTIONAL { ?d rdfs:comment ?comment . }
                                                 OPTIONAL { ?d foaf:homepage ?homepage . }
                                                 OPTIONAL { ?d dbo:numberOfEmployees ?employeeCount . }
                                            } ORDER BY ?name""", Developer.class).getResultList();
    }

    @Override
    public List<Developer> findByName(String name) {
        return em.createNativeQuery("""
                                            PREFIX foaf: <http://xmlns.com/foaf/0.1/>
                                            PREFIX dbo: <http://dbpedia.org/ontology/>
                                            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                                            SELECT ?d ?comment ?homepage ?employeeCount ?types WHERE {
                                              ?d a <http://dbpedia.org/class/yago/WikicatVideoGameDevelopmentCompanies> ;
                                                 foaf:name ?name ;
                                                 a ?types .
                                              OPTIONAL { ?d rdfs:comment ?comment . }
                                              OPTIONAL { ?d foaf:homepage ?homepage . }
                                              OPTIONAL { ?d dbo:numberOfEmployees ?employeeCount . }
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
                                            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                                            SELECT ?d ?comment ?homepage ?employeeCount ?types WHERE {
                                              ?d a <http://dbpedia.org/class/yago/WikicatVideoGameDevelopmentCompanies> ;
                                                 foaf:name ?name ;
                                                 a ?types .
                                              OPTIONAL { ?d rdfs:comment ?comment . }
                                              OPTIONAL { ?d foaf:homepage ?homepage . }
                                              OPTIONAL { ?d dbo:numberOfEmployees ?employeeCount . }
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
        return QueryMechanism.SPARQL_MAPPING;
    }
}
