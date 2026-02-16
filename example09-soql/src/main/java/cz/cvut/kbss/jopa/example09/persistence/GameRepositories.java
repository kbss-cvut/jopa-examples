package cz.cvut.kbss.jopa.example09.persistence;

import cz.cvut.kbss.jopa.example09.model.Developer;
import cz.cvut.kbss.jopa.example09.model.Game;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class GameRepositories {

    private final List<GameRepository> implementations;

    public GameRepositories(List<GameRepository> implementations) {
        this.implementations = implementations;
    }

    private GameRepository resolveImplementation(QueryMechanism mechanism) {
        return implementations.stream()
                              .filter(i -> i.getQueryMechanism() == mechanism)
                              .findFirst()
                              .orElseThrow(() -> new IllegalArgumentException(
                                      "No implementation found for mechanism: " + mechanism));
    }

    public List<Game> findAll(QueryMechanism queryMechanism) {
        return resolveImplementation(queryMechanism).findAll();
    }

    public List<Game> findAll(LocalDate from, LocalDate to, QueryMechanism queryMechanism) {
        return resolveImplementation(queryMechanism).findAll(from, to);
    }

    public List<Game> findAll(Developer developer, QueryMechanism queryMechanism) {
        return resolveImplementation(queryMechanism).findAll(developer);
    }

    public List<Game> findAllBySmallDevelopers(QueryMechanism queryMechanism) {
        return resolveImplementation(queryMechanism).findAllBySmallDevelopers();
    }
}
