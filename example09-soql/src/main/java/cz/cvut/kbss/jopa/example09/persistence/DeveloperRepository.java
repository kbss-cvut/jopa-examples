package cz.cvut.kbss.jopa.example09.persistence;

import cz.cvut.kbss.jopa.example09.model.Developer;

import java.util.List;
import java.util.Optional;

public interface DeveloperRepository {

    String NAMESPACE = "http://dbpedia.org/resource/";
    int SMALL_DEVELOPER_SIZE = 100;

    List<Developer> findAll();

    List<Developer> findByName(String name);

    List<Developer> findSmallDevelopers();

    Optional<Developer> findById(String localName);

    QueryMechanism getQueryMechanism();
}
