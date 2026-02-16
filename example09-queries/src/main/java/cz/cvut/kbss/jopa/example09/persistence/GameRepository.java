package cz.cvut.kbss.jopa.example09.persistence;

import cz.cvut.kbss.jopa.example09.model.Developer;
import cz.cvut.kbss.jopa.example09.model.Game;

import java.time.LocalDate;
import java.util.List;

public interface GameRepository {
    List<Game> findAll();

    List<Game> findAll(LocalDate from, LocalDate to);

    List<Game> findAll(Developer developer);

    List<Game> findAllBySmallDevelopers();

    QueryMechanism getQueryMechanism();
}
