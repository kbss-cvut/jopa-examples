# Example 07 - JOPA with Plural Data Properties and Inheritance

This example demonstrates JOPA's support for inheritance and plural data properties.

### Features

* Spring boot application with REST services,
* Publishing data in JSON-LD,
* Single inheritance, polymorphic instance loading and polymorphic attributes,
* Plural datatype property values.

### Inheritance

Support for single inheritance has been added to JOPA in version 0.9.0. It enables the developer to declare an entity to be a subclass
of another entity and leverage polymorphic behavior, for instance by requesting a parent type when loading an entity and letting JOPA
resolve which concrete implementation should the object be an instance of.

This is shown by the `Report`, `OccurrenceReport`, `AuditReport` and `SafetyIssueReport` classes in the demo. `Report` is an abstract
superclass of all of the other. When loading reports, the abstract `Report` class is given as return type. JOPA then has to determine
for each loaded instance to which concrete subtype of `Report` it belongs.

Notice also how `SafetyIssueReport` contains an attribute with references to other reports. This shows that polymorphic attribute values
are also supported by JOPA. Polymorphic loading works both in the `EntityManager.find` method as well as for query results, as can
be seen in `ReportDao`.

[comment]: # (TODO Add link to papers describing inheritance)

### Plural Data Properties

JOPA 0.8.8 also added support for polymorphic datatype properties. This is shown in class `AuditReport`, which contains a set of audit findings,
which are plain string values.

### JSON-LD Support

[JAXB JSON-LD](https://github.com/kbss-cvut/jaxb-jsonld) is a library for serialization and deserialization of POJOs into/from JSON-LD.

It simplifies publishing data processed by business applications in Linked-Data format.

The [JAXB JSON-LD Jackson](https://github.com/kbss-cvut/jaxb-jsonld-jackson) integration enables seamless integration of 
the JSON-LD support in any application using [Jackson](https://github.com/FasterXML/jackson) as serialization/deserialization library.

More information can be found in the [JSON-LD integration demo](https://github.com/kbss-cvut/jopa-examples/tree/master/jsonld).

## Persistence Setup

The persistence is set up in `cz.cvut.kbss.jopa.example07.persistence.PersistenceFactory`. We are using a native Sesame repository, stored in `/tmp`.
Of course, this setup can be changed. Target location is in `config.properties`.

## Running the Demo

To run the demo, `mvn spring-boot:run` can be used.
