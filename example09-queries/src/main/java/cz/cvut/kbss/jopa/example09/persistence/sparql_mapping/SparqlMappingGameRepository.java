package cz.cvut.kbss.jopa.example09.persistence.sparql_mapping;

import cz.cvut.kbss.jopa.example09.model.Developer;
import cz.cvut.kbss.jopa.example09.model.Game;
import cz.cvut.kbss.jopa.example09.persistence.DeveloperRepository;
import cz.cvut.kbss.jopa.example09.persistence.GameRepository;
import cz.cvut.kbss.jopa.example09.persistence.QueryMechanism;
import cz.cvut.kbss.jopa.model.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class SparqlMappingGameRepository implements GameRepository {

    private final EntityManager em;

    public SparqlMappingGameRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Game> findAll() {
        return em.createNativeQuery("""
                                            PREFIX dbo: <http://dbpedia.org/ontology/>
                                            PREFIX foaf: <http://xmlns.com/foaf/0.1/>
                                            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                                            SELECT ?g ?name ?comment ?developer ?releaseDate ?types WHERE {
                                              ?g a dbo:VideoGame ;
                                                 foaf:name ?name ;
                                                 dbo:releaseDate ?releaseDate ;
                                                 a ?types .
                                              OPTIONAL { ?g rdfs:comment ?comment . }
                                              OPTIONAL { ?g dbo:developer ?developer . }
                                            } ORDER BY DESC(?releaseDate)""",
                                    Game.class)
                 .getResultList();
    }

    @Override
    public List<Game> findAll(LocalDate from, LocalDate to) {
        return em.createNativeQuery("""
                                            PREFIX dbo: <http://dbpedia.org/ontology/>
                                            PREFIX foaf: <http://xmlns.com/foaf/0.1/>
                                            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                                            SELECT ?g ?name ?comment ?developer ?releaseDate ?types WHERE {
                                              ?g a dbo:VideoGame ;
                                                 foaf:name ?name ;
                                                 dbo:releaseDate ?releaseDate ;
                                                 a ?types .
                                              OPTIONAL { ?g rdfs:comment ?comment . }
                                              OPTIONAL { ?g dbo:developer ?developer . }
                                              FILTER (?releaseDate >= ?from && ?releaseDate < ?to)
                                            } ORDER BY DESC(?releaseDate)""",
                                    Game.class)
                 .setParameter("from", from)
                 .setParameter("to", to)
                 .getResultList();
    }

    @Override
    public List<Game> findAll(Developer developer) {
        return em.createNativeQuery("""
                                            PREFIX dbo: <http://dbpedia.org/ontology/>
                                            PREFIX foaf: <http://xmlns.com/foaf/0.1/>
                                            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                                            SELECT ?g ?name ?comment ?developer ?releaseDate ?types WHERE {
                                              ?g a dbo:VideoGame ;
                                                 foaf:name ?name ;
                                                 dbo:releaseDate ?releaseDate ;
                                                 a ?types .
                                              OPTIONAL { ?g rdfs:comment ?comment . }
                                              OPTIONAL { ?g dbo:developer ?developer . }
                                            } ORDER BY DESC(?releaseDate)""",
                                    Game.class)
                 .setParameter("developer", developer)
                 .getResultList();
    }

    @Override
    public List<Game> findAllBySmallDevelopers() {
        return em.createNativeQuery("""
                                            PREFIX dbo: <http://dbpedia.org/ontology/>
                                            PREFIX foaf: <http://xmlns.com/foaf/0.1/>
                                            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                                            SELECT ?g ?name ?comment ?developer ?releaseDate ?types WHERE {
                                              ?g a dbo:VideoGame ;
                                                 foaf:name ?name ;
                                                 dbo:releaseDate ?releaseDate ;
                                                 dbo:developer ?developer ;
                                                 a ?types .
                                              OPTIONAL { ?g rdfs:comment ?comment . }
                                              ?developer dbo:numberOfEmployees ?employeeCount .
                                              FILTER (?employeeCount < ?smallDeveloperThreshold)
                                            } ORDER BY DESC(?releaseDate)""",
                                    Game.class)
                 .setParameter("smallDeveloperThreshold", DeveloperRepository.SMALL_DEVELOPER_SIZE)
                 .getResultList();
    }

    @Override
    public QueryMechanism getQueryMechanism() {
        return QueryMechanism.SPARQL_MAPPING;
    }
}
