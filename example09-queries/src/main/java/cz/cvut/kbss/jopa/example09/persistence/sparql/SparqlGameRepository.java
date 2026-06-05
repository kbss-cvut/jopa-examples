package cz.cvut.kbss.jopa.example09.persistence.sparql;

import cz.cvut.kbss.jopa.example09.model.Developer;
import cz.cvut.kbss.jopa.example09.model.Game;
import cz.cvut.kbss.jopa.example09.persistence.DeveloperRepository;
import cz.cvut.kbss.jopa.example09.persistence.GameRepository;
import cz.cvut.kbss.jopa.example09.persistence.QueryMechanism;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.query.TypedQuery;
import cz.cvut.kbss.jopa.query.QueryHints;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class SparqlGameRepository implements GameRepository {

    private final EntityManager em;

    public SparqlGameRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Game> findAll(boolean withFetchGraph) {
        final TypedQuery<Game> query = em.createNativeQuery("""
                                            PREFIX dbo: <http://dbpedia.org/ontology/>
                                            SELECT ?g WHERE {
                                              ?g a dbo:VideoGame ;
                                                 dbo:releaseDate ?releaseDate .
                                            } ORDER BY DESC(?releaseDate)""",
                                    Game.class);
        if (withFetchGraph) {
            query.setHint(QueryHints.FETCH_GRAPH, em.getEntityGraph("Game.findAll"));
        }
        return query.getResultList();
    }

    @Override
    public List<Game> findAll(LocalDate from, LocalDate to, boolean withFetchGraph) {
        final TypedQuery<Game> query = em.createNativeQuery("""
                                            PREFIX dbo: <http://dbpedia.org/ontology/>
                                            SELECT ?g WHERE {
                                              ?g a dbo:VideoGame ;
                                                 dbo:releaseDate ?releaseDate .
                                              FILTER (?releaseDate >= ?from && ?releaseDate < ?to)
                                            } ORDER BY DESC(?releaseDate)""",
                                    Game.class)
                 .setParameter("from", from)
                 .setParameter("to", to);
        if (withFetchGraph) {
            query.setHint(QueryHints.FETCH_GRAPH, em.getEntityGraph("Game.findAll"));
        }
        return query.getResultList();
    }

    @Override
    public List<Game> findAll(Developer developer, boolean withFetchGraph) {
        final TypedQuery<Game> query = em.createNativeQuery("""
                                            PREFIX dbo: <http://dbpedia.org/ontology/>
                                            SELECT ?g WHERE {
                                              ?g a dbo:VideoGame ;
                                                 dbo:releaseDate ?releaseDate ;
                                                 dbo:developer ?developer .
                                            } ORDER BY DESC(?releaseDate)""",
                                    Game.class)
                 .setParameter("developer", developer);
        if (withFetchGraph) {
            query.setHint(QueryHints.FETCH_GRAPH, em.getEntityGraph("Game.findAll"));
        }
        return query.getResultList();
    }

    @Override
    public List<Game> findAllBySmallDevelopers(boolean withFetchGraph) {
        final TypedQuery<Game> query = em.createNativeQuery("""
                                            PREFIX dbo: <http://dbpedia.org/ontology/>
                                            SELECT ?g WHERE {
                                              ?g a dbo:VideoGame ;
                                                 dbo:releaseDate ?releaseDate ;
                                                 dbo:developer ?developer .
                                              ?developer dbo:numberOfEmployees ?employeeCount .
                                              FILTER (?employeeCount < ?smallDeveloperThreshold)
                                            } ORDER BY DESC(?releaseDate)""",
                                    Game.class)
                 .setParameter("smallDeveloperThreshold", DeveloperRepository.SMALL_DEVELOPER_SIZE);
        if (withFetchGraph) {
            query.setHint(QueryHints.FETCH_GRAPH, em.getEntityGraph("Game.findAll"));
        }
        return query.getResultList();
    }

    @Override
    public QueryMechanism getQueryMechanism() {
        return QueryMechanism.SPARQL;
    }
}
