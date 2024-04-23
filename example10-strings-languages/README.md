# JOPA Example 10 - Working with Strings and Languages

This example provides insight into how strings and languages work in JOPA.

### Features

Main JOPA features shown in this demo include:

- Persistence unit-level language setting
- Simple literals [1]
- Multilingual strings
- Package-level namespace declaration

#### Persistence Unit Language

It is possible to set a language for the whole persistence unit (using the `cz.cvut.jopa.lang` property). This will mean
all String-valued attributes will be stored with the specified language tag (and read using this language tag as well).

This global language setting can be overridden using `Descriptor`s or by specifying individual attributes as _simple literals_.

Simple literals are stored without any language tag because they represent the RDF `http://www.w3.org/2001/XMLSchema#string`
datatype. Marking a String-valued attribute as simple literal is as simple as setting `simpleLiteral = true` in the
`@OWLDataProperty` or `@OWLAnnotationProperty` mapping annotation.

#### Multilingual Strings

RDF has very good support for internationalized data as it allows saving strings with tags indicating the language of the
string value. This way, translations of a value can be easily provided.

JOPA supports multilingual strings via the `MultilingualString` class which can be used as an attribute type. This class
is basically a map of language tags to corresponding values.

#### Namespaces

JOPA allows declaring namespaces that can then be used in mapping IRIs. These namespaces can be declared on class or package
level either individually (using the `@Namespace` annotation), or wrapped in the `@Namespaces` annotation. In this example,
we declare the well-known FOAF ontology namespace on the whole model package (using the `package-info.java` file) so that
we can then type `foaf:Person` instead of `http://xmlns.com/foaf/0.1/Person`.

## Persistence Setup

The persistence is set up in `cz.cvut.kbss.jopa.example10.PersistenceFactory`. The demo uses two persistence units with
underlying RDf4J in-memory repositories.

## Running the Demo

To run the demo, `mvn exec:java` or `gradle runApp` can be used. The demo outputs the stored data into `withPersistenceUnitLanguage.ttl` and
`withoutPersistenceUnitLanguage.ttl` files so that you can verify what actually gets stored under different language-related
configurations.

## References

[1] RDF 1.1 Concepts and Abstract
Syntax. [https://www.w3.org/TR/rdf11-concepts/#section-Graph-Literal](https://www.w3.org/TR/rdf11-concepts/#section-Graph-Literal)
