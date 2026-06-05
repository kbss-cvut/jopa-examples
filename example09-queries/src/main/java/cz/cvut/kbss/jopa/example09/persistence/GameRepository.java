package cz.cvut.kbss.jopa.example09.persistence;

import cz.cvut.kbss.jopa.example09.model.Developer;
import cz.cvut.kbss.jopa.example09.model.Game;

import java.time.LocalDate;
import java.util.List;

public interface GameRepository {
    List<Game> findAll(boolean withFetchGraph);

    List<Game> findAll(LocalDate from, LocalDate to, boolean withFetchGraph);

    List<Game> findAll(Developer developer, boolean withFetchGraph);

    List<Game> findAllBySmallDevelopers(boolean withFetchGraph);

    QueryMechanism getQueryMechanism();
}
