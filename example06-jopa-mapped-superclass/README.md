# JOPA Example 06 - JOPA + Mapped Superclass Support

This example showcases JOPA's support for mapped superclasses.

### Features

* `@MappedSuperclass` support
* Plain URI object property values

### Mapped Superclass

Mapped superclasses allow entities to inherit common attributes and behaviour. However, the classes themselves are not mapped
to any ontological classes. Their attributes are added to the attributes of entity classes which extend them.

In this case, the `AbstractEntity` represents a common persistence idiom, in which there is a single abstract mapped superclass,
which declares an identifier, and is extended by all entities in the model. In our model, there is another mapped superclass -
`Report`, which declares attributes common to both `AuditReport` and `OccurrenceReport`.

### Plain URI Object Property Values

JOPA now supports use of identifiers instead of full entities as object property values. This enables the model to be connected
to parts of the ontology which are not mapped by it. The identifier-valued properties are always eagerly fetched and no
range check is performed, as there is no knowledge about the target of the relationship in the model.

## Persistence Setup

The persistence is set up in `cz.cvut.kbss.jopa.example06.persistence.PersistenceFactory`. We are using an in-memory Sesame repository,
which is thrown away after the application exits.

## Running the Demo

To run the demo, `mvn exec:java` can be used.
