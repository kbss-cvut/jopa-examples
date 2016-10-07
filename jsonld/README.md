# JSON-LD - JOPA + JAXB-JSON-LD Jackson

This example showcases usage of JOPA model and Jackson integration of the [JAXB-JSON-LD](https://github.com/kbss-cvut/jaxb-jsonld-jackson) library.

### Features

* Spring boot application with REST services,
* Publishing data in JSON-LD.

### JSON-LD Support

[JAXB JSON-LD](https://github.com/kbss-cvut/jaxb-jsonld) is a library for serialization and deserialization of POJOs into/from JSON-LD.

It simplifies publishing data processed by business applications in Linked-Data format.

The JAXB JSON-LD Jackson integration enables seamless integration of the JSON-LD support in any application using 
[Jackson](https://github.com/FasterXML/jackson) as serialization/deserialization library.

This demo showcases easy integration of both technologies with Spring.

## Persistence Setup

The persistence is set up in `cz.cvut.kbss.jopa.jsonld.persistence.PersistenceFactory`. We are using an in-memory Sesame repository,
which is thrown away after the application exits.

## Running the Demo

To run the demo, `mvn exec:java` can be used.
