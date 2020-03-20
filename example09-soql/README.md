# JOPA + Semantic Object Query Language

This example showcases support for the Semantic Object Query Language (SOQL) in JOPA.

## Features

* Spring boot application with REST services,
* Publishing data in JSON-LD,
* Jena file storage,
* Autowiring `EntityManager` with Spring,
* SOQL.

### Semantic Object Query Language

SOQL is an object query language similar to JPQL intended to simplify querying of semantic data based on application object model.
It allows to use names of classes and attributes instead of having to write out full IRIs (or declaring prefixes) in a SPARQL query.
More information about the language and its current limits is available at the project's [wiki](https://github.com/kbss-cvut/jopa/wiki/Semantic-Object-Query-Language).

The queries are used in the `DeveloperRepository` and `GameRepository` classes and should be self-explanatory.

### JSON-LD Support

The demo uses [JB4JSON-LD](https://github.com/kbss-cvut/jb4jsonld) to publish JSON-LD data. More specifically,
we are using the [JB4JSON-LD Jackson](https://github.com/kbss-cvut/jb4jsonld-jackson) integration, which allows the
REST API to work with [Jackson](https://github.com/FasterXML/jackson) as most Spring applications do.


### Persistence Setup

The persistence is set up in `cz.cvut.kbss.jopa.example09.persistence.PersistenceFactory`. We are using Jena file-based repository.
The file contains sample data exported from [DBPedia](https://dbpedia.org) and its location is set up in `application.properties`.

### Autowired `EntityManager`

This demo makes use of the [JOPA-Spring-transaction](https://github.com/ledsoft/jopa-spring-transaction) library, 
which enables JOPA to be used together with Spring's declarative transactions. In this concrete case, no transactions
are need. However, the library allows us to autowire `EntityManager` instances into data access objects via the
 `JopaTransactionManager` and `DelegatingEntityManager` Spring beans. 
 See `cz.cvut.kbss.jopa.example09.config.PersistenceConfig` for reference.

## Running the Demo

To run the demo, `mvn spring-boot:run` can be used. The REST API is available at [http://localhost:18119/example09/rest](http://localhost:18119/example09/rest)
(configured in `application.properties`).

The following endpoints are available:

* **GET** `/developers` - gets a list of all developers in the file, optional `name` query parameter allows to search for a developer using a name pattern
* **GET** `/developers/small` - gets a list of small developers, which have at most 100 employees
* **GET** `/developers/small/games` - gets a list of games by the small developers
* **GET** `/developers/{localName}` - gets a developer with an identifier consisting of the specified local name and the `http://dbpedia.org/resource/` namespace
* **GET** `/developers/{localName}/games` - gets a list of games by the specified developer
* **GET** `/games` - gets a list of all games in the file. Optional `from` and `to` parameters can be used to specified release date period (date in ISO format)



