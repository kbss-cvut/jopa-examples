# JOPA + Jena Driver + Polymorphism

This example showcases usage of JOPA with the Jena OntoDriver working on top of Jena TDB.
It also uses a polymorphic object model.

## Features

* Spring boot application with REST services,
* Publishing data in JSON-LD,
* Consuming data in JSON-LD,
* Jena TDB storage,
* Declarative transactions,
* Polymorphic object model,
* Basic RDFS inference.

### JSON-LD Support

The demo uses [JB4JSON-LD](https://github.com/kbss-cvut/jb4jsonld) to publish/consume JSON-LD data. More specifically,
we are using the [JB4JSON-LD Jackson](https://github.com/kbss-cvut/jb4jsonld-jackson) integration, which allows the
REST API to work with [Jackson](https://github.com/FasterXML/jackson) as most Spring applications do.


### Persistence Setup

The persistence is set up in `cz.cvut.kbss.jopa.example08.persistence.PersistenceFactory`. We are using Jena TDB repository.
Its location is set up in `application.properties`.

### Declarative Transactions

This demo makes use of the [JOPA-Spring-transaction](https://github.com/ledsoft/jopa-spring-transaction) library, 
which enables JOPA to be used together with Spring's declarative transactions. See the services for usage example.
To make the transactions work, it is necessary to instantiate the `JopaTransactionManager` and `DelegatingEntityManager` Spring beans.
See `cz.cvut.kbss.jopa.example08.config.PersistenceConfig` for reference.

## Running the Demo

To run the demo, `mvn spring-boot:run` can be used. The REST API is available at [http://localhost:18115/example08/rest](http://localhost:18115/example08/rest)
(configured in `application.properties`).

### Sample Input

Sample data which can be used to create a report may look as follows:

```JSON
{
    "@type": [
        "http://onto.fel.cvut.cz/ontologies/jopa/example08/report",
        "http://onto.fel.cvut.cz/ontologies/jopa/example08/occurrence-report"
    ],
    "http://purl.org/dc/terms/description": "This is a new report I just created.",
    "http://onto.fel.cvut.cz/ontologies/jopa/example08/documents": {
        "@type": [
            "http://onto.fel.cvut.cz/ontologies/eccairs/aviation-3.4.0.2/vl-a-430/v-10",
            "http://onto.fel.cvut.cz/ontologies/ufo/event",
            "http://onto.fel.cvut.cz/ontologies/jopa/example08/occurrence"
        ],
        "http://www.w3.org/2000/01/rdf-schema#label": "New Occurrence 1",
        "http://onto.fel.cvut.cz/ontologies/jopa/example08/severity": 4,
        "http://onto.fel.cvut.cz/ontologies/jopa/example08/start": "Fri Apr 27 14:12:52 CEST 2018",
        "http://onto.fel.cvut.cz/ontologies/ufo/has-part": [
            {
                "@type": [
                    "http://onto.fel.cvut.cz/ontologies/aviation-safety/Bird_strike",
                    "http://onto.fel.cvut.cz/ontologies/ufo/event"
                ],
                "http://onto.fel.cvut.cz/ontologies/jopa/example08/start": "Fri Apr 27 14:12:52 CEST 2018",
                "http://onto.fel.cvut.cz/ontologies/jopa/example08/end": "Fri Apr 27 14:13:02 CEST 2018"
            }
        ],
        "http://onto.fel.cvut.cz/ontologies/jopa/example08/end": "Fri Apr 27 14:13:02 CEST 2018"
    }
}
```

