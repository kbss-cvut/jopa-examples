package cz.cvut.kbss.jopa.example09.persistence;

import cz.cvut.kbss.jopa.example09.model.Developer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DeveloperRepositories {

    private final List<DeveloperRepository> implementations;

    public DeveloperRepositories(List<DeveloperRepository> implementations) {
        this.implementations = implementations;
    }

    private DeveloperRepository resolveImplementation(QueryMechanism mechanism) {
        return implementations.stream()
                              .filter(i -> i.getQueryMechanism() == mechanism)
                              .findFirst()
                              .orElseThrow(() -> new IllegalArgumentException(
                                      "No implementation found for mechanism: " + mechanism));
    }

    public List<Developer> findAll(QueryMechanism mechanism) {
        return resolveImplementation(mechanism).findAll();
    }

    public List<Developer> findByName(String name, QueryMechanism mechanism) {
        return resolveImplementation(mechanism).findByName(name);
    }

    public List<Developer> findSmallDevelopers(QueryMechanism mechanism) {
        return resolveImplementation(mechanism).findSmallDevelopers();
    }

    public Optional<Developer> findById(String localName, QueryMechanism mechanism) {
        return resolveImplementation(mechanism).findById(localName);
    }
}
