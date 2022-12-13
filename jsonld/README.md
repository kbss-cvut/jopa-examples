# JSON-LD - JOPA + JB4JSON-LD Jackson

This example showcases usage of JOPA model and Jackson integration of the [JB4JSON-LD](https://github.com/kbss-cvut/jb4jsonld-jackson) library.

### Features

* Spring boot application with REST services,
* Publishing data in JSON-LD with context,
* Consuming data in JSON-LD,
* REST API supporting both JSON and JSON-LD.

### JSON-LD Support

[JB4JSON-LD](https://github.com/kbss-cvut/jb4jsonld) is a library for serialization and deserialization of POJOs into/from JSON-LD.

It simplifies publishing data processed by business applications in Linked-Data format.

The [JB4JSON-LD Jackson](https://github.com/kbss-cvut/jb4jsonld-jackson) integration enables seamless integration of 
the JSON-LD support in any application using [Jackson](https://github.com/FasterXML/jackson) as serialization/deserialization library.

This demo showcases easy integration of both technologies with Spring.


#### Serialization

Some examples of the JSON-LD serialization follow. The resulting JSON-LD is valid and can be verified e.g. in the [JSON-LD playground](http://json-ld.org/playground/).

**Simple instance serialization**

```json
{
  "@context": {
    "emailAddress": "http://xmlns.com/foaf/0.1/mbox",
    "dateCreated": {
      "@id": "http://purl.org/dc/terms/created",
      "@type": "http://www.w3.org/2001/XMLSchema#dateTime"
    },
    "name": "http://www.w3.org/2000/01/rdf-schema#label",
    "uri": "@id",
    "key": "http://krizik.felk.cvut.cz/ontologies/study-manager/key"
  },
  "uri": "http://krizik.felk.cvut.cz/ontologies/study-manager/Organization-1048030208",
  "@type": [
    "http://krizik.felk.cvut.cz/ontologies/study-manager/organization"
  ],
  "name": "Organization-1048030208",
  "emailAddress": "organization-1048030208@jopaexample.org",
  "dateCreated": "2022-11-29T13:56:08.569Z",
  "key": "1669730168569"
}
```

**Instance tree**
```json
{
  "@context": {
    "firstName": "http://xmlns.com/foaf/0.1/firstName",
    "lastName": "http://xmlns.com/foaf/0.1/lastName",
    "types": "@type",
    "emailAddress": "http://xmlns.com/foaf/0.1/mbox",
    "dateCreated": {
      "@id": "http://purl.org/dc/terms/created",
      "@type": "http://www.w3.org/2001/XMLSchema#dateTime"
    },
    "name": "http://www.w3.org/2000/01/rdf-schema#label",
    "clinic": "http://krizik.felk.cvut.cz/ontologies/study-manager/is-member-of",
    "uri": "@id",
    "key": "http://krizik.felk.cvut.cz/ontologies/study-manager/key"
  },
  "uri": "http://krizik.felk.cvut.cz/ontologies/study-manager/FirstName-462003349+LastName1393253798",
  "types": [
    "http://krizik.felk.cvut.cz/ontologies/study-manager/user",
    "http://krizik.felk.cvut.cz/ontologies/study-manager/doctor"
  ],
  "dateCreated": "2022-11-29T13:56:08.608Z",
  "emailAddress": "FirstName-462003349.LastName1393253798@jopaexample.org",
  "firstName": "FirstName-462003349",
  "lastName": "LastName1393253798",
  "clinic": {
    "uri": "http://krizik.felk.cvut.cz/ontologies/study-manager/Organization847735561",
    "@type": [
      "http://krizik.felk.cvut.cz/ontologies/study-manager/organization"
    ],
    "name": "Organization847735561",
    "emailAddress": "organization847735561@jopaexample.org",
    "dateCreated": "2022-11-29T13:56:08.569Z",
    "key": "1669730168589"
  },
  "key": "1669730168609"
}
```

**Multiple reference serialization**
```json
{
  "@context": {
    "firstName": "http://xmlns.com/foaf/0.1/firstName",
    "lastName": "http://xmlns.com/foaf/0.1/lastName",
    "types": "@type",
    "emailAddress": "http://xmlns.com/foaf/0.1/mbox",
    "dateCreated": {
      "@id": "http://purl.org/dc/terms/created",
      "@type": "http://www.w3.org/2001/XMLSchema#dateTime"
    },
    "created": {
      "@id": "http://purl.org/dc/terms/created",
      "@type": "http://www.w3.org/2001/XMLSchema#dateTime"
    },
    "name": "http://www.w3.org/2000/01/rdf-schema#label",
    "clinic": "http://krizik.felk.cvut.cz/ontologies/study-manager/is-member-of",
    "uri": "@id",
    "key": "http://krizik.felk.cvut.cz/ontologies/study-manager/key",
    "participants": "http://krizik.felk.cvut.cz/ontologies/study-manager/has-participant"
  },
  "uri": "http://krizik.felk.cvut.cz/ontologies/study-manager/study/instance1205137700",
  "@type": [
    "http://krizik.felk.cvut.cz/ontologies/study-manager/study"
  ],
  "name": "Study-318205302",
  "participants": [
    {
      "uri": "http://krizik.felk.cvut.cz/ontologies/study-manager/FirstName1206748040+LastName-675804197",
        "types": [
          "http://krizik.felk.cvut.cz/ontologies/study-manager/user",
          "http://krizik.felk.cvut.cz/ontologies/study-manager/doctor"
        ],
        "dateCreated": "2022-11-29T13:56:08.608Z",
        "emailAddress": "FirstName1206748040.LastName-675804197@jopaexample.org",
        "firstName": "FirstName1206748040",
        "lastName": "LastName-675804197",
        "clinic": {
          "uri": "http://krizik.felk.cvut.cz/ontologies/study-manager/Organization847735561",
          "@type": [
              "http://krizik.felk.cvut.cz/ontologies/study-manager/organization"
          ],
          "name": "Organization847735561",
          "emailAddress": "organization847735561@jopaexample.org",
          "dateCreated": "2022-11-29T13:56:08.569Z",
          "key": "1669730168589"
        },
        "key": "1669730168625"
    },
    {
        "uri": "http://krizik.felk.cvut.cz/ontologies/study-manager/FirstName-462003349+LastName1393253798",
        "types": [
          "http://krizik.felk.cvut.cz/ontologies/study-manager/user",
          "http://krizik.felk.cvut.cz/ontologies/study-manager/doctor"
        ],
        "dateCreated": "2022-11-29T13:56:08.608Z",
        "emailAddress": "FirstName-462003349.LastName1393253798@jopaexample.org",
        "firstName": "FirstName-462003349",
        "lastName": "LastName1393253798",
        "clinic": {
          "uri": "http://krizik.felk.cvut.cz/ontologies/study-manager/Organization847735561"
        },
        "key": "1669730168609"
    }
  ],
  "key": "1669730168658",
  "created": "2022-11-29T13:56:08.651Z"
}
```
Notice how attribute `clinic` of the user `http://krizik.felk.cvut.cz/ontologies/study-manager/FirstName-462003349+LastName1393253798`
uses only the IRI of the organization it is a member of, because the organization itself has been already used in the document.

This mechanism also naturally solves the issue of backward references, which has to be resolved using `JsonIdentityInfo` in Jackson. JSON-LD contains
this feature by default.

#### Deserialization

_JB4JSON-LD_ is internally using [jsonld-java](https://github.com/jsonld-java/jsonld-java) and can thus handle any
of the JSON-LD formats supported by this library (compacted, expanded, flattened). 
It can also handle reference serialization through IRIs described in the previous section.

As an example, the following JSON-LD can be passed to the application to create a new study (POST to `/rest/studies`):

```json
{
  "@type": [
    "http://krizik.felk.cvut.cz/ontologies/study-manager/study"
  ],
  "http://www.w3.org/2000/01/rdf-schema#label": "LupusStudy",
  "http://krizik.felk.cvut.cz/ontologies/study-manager/has-admin": [
    {
      "@type": [
        "http://krizik.felk.cvut.cz/ontologies/study-manager/user"
      ],
      "http://purl.org/dc/terms/created": "2022-11-29T13:56:08Z",
      "http://xmlns.com/foaf/0.1/mbox": "Lisa.Cuddy@jopaexample.org",
      "http://xmlns.com/foaf/0.1/firstName": "Lisa",
      "http://xmlns.com/foaf/0.1/lastName": "Cuddy",
      "http://krizik.felk.cvut.cz/ontologies/study-manager/is-member-of": {
        "@type": [
          "http://krizik.felk.cvut.cz/ontologies/study-manager/organization"
        ],
        "http://www.w3.org/2000/01/rdf-schema#label": "Princeton-Plainsboro Teaching Hospital",
        "http://xmlns.com/foaf/0.1/mbox": "princetono-plainsboro@jopaexample.org",
        "http://purl.org/dc/terms/created": "2022-11-29T13:56:08Z",
        "@id": "http://krizik.felk.cvut.cz/ontologies/study-manager/Organization-PrincetonPlainsboro",
        "http://krizik.felk.cvut.cz/ontologies/study-manager/key": "1476113644121"
      },
      "@id": "http://krizik.felk.cvut.cz/ontologies/study-manager/Lisa+Cuddy",
      "http://krizik.felk.cvut.cz/ontologies/study-manager/key": "1476113644169"
    }
  ],
  "http://krizik.felk.cvut.cz/ontologies/study-manager/has-participant": [
    {
      "@type": [
        "http://krizik.felk.cvut.cz/ontologies/study-manager/user"
      ],
      "http://purl.org/dc/terms/created": "2022-11-29T13:56:08Z",
      "http://xmlns.com/foaf/0.1/mbox": "Greg.House@jopaexample.org",
      "http://xmlns.com/foaf/0.1/firstName": "Greg",
      "http://xmlns.com/foaf/0.1/lastName": "House",
      "http://krizik.felk.cvut.cz/ontologies/study-manager/is-member-of": {
         "@id": "http://krizik.felk.cvut.cz/ontologies/study-manager/Organization-PrincetonPlainsboro"
      },
      "@id": "http://krizik.felk.cvut.cz/ontologies/study-manager/Greg+House",
      "http://krizik.felk.cvut.cz/ontologies/study-manager/key": "1476113644168"
    }
  ],
  "@id": "http://krizik.felk.cvut.cz/ontologies/study-manager/study#instance-1587729056",
  "http://krizik.felk.cvut.cz/ontologies/study-manager/key": "1476113644225",
  "http://purl.org/dc/terms/created": "2022-11-29T13:56:08Z"
}
```

Notice how the Dr. House's organization membership references the previously declared Princeton Plainsboro hospital.

### JSON and JSON-LD

The REST API of this application is configured to support both JSON and JSON-LD. To do so, two `ObjectMapper`s have to be created.
One contains the JSON-LD module, the other has regular configuration. A new `HttpMessageConverter` is then registered,using the JSON-LD `ObjectMapper` supporting JSON-LD media type.
The actual configuration can be found in `WebAppConfig`.

### REST API Documentation

OpenAPI documentation with Swagger UI is provided by the demo. Both are provided by the [SpringDoc](https://springdoc.org/) library.

- http://localhost:18117/jsonld/v3/api-docs can be used to access the API docs.
- http://localhost:18117/jsonld/swagger-ui/index.html then to access the Swagger UI with API documentation.


## Persistence Setup

The persistence is set up in `cz.cvut.kbss.jopa.jsonld.persistence.PersistenceFactory`. We are using a native RDF4J repository, stored in `/tmp`.
Of course, this setup can be changed. Target location is in `config.properties`.

### Declarative Transactions

This demo makes use of the [JOPA-Spring-transaction](https://github.com/ledsoft/jopa-spring-transaction) library, 
which enables JOPA to be used together with Spring's declarative transactions. See the services for usage example.
To make the transactions work, it is necessary to instantiate the `JopaTransactionManager` and `DelegatingEntityManager` Spring beans.
See `cz.cvut.kbss.jopa.jsonld.config.PersistenceConfig` for reference.


### Declarative Transactions in Tests

Since JOPA does not understand SPARQL queries, uncommitted changes are not visible to queries during transaction. Therefore, 
the whole tests cannot be executed in one transaction, as is common in Spring applications. Instead, methods modifying
the state of the repository must be executed using the Spring's `TransactionTemplate`. See 
`cz.cvut.kbss.jopa.jsonld.persistence.dao.BaseDaoTestRunner` for example and an explanation. 

## Running the Demo

To run the demo, `mvn spring-boot:run` can be used.
