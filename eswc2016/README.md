# JOPA ESWC 2016 Demo

This is a demo of JOPA features for the ESWC 2016 conference.

### Features

This example does not show any significant JOPA features. To see such examples, consult the other examples. Instead, this
demo shows integration of JOPA with CDI frameworks like Spring.

#### EntityManagerFactory as a Spring Bean

Most importantly, JOPA's `EntityManagerFactory` (EMF) is created as a Spring bean and managed by the Spring context. The class 
`cz.cvut.kbss.jopa.eswc2016.persistence.PersistenceFactory` after its initialization instantiates EMF and provides it as a bean. 
The EMF instance is closed before application shutdown.

#### EntityManager in Data Access Objects

TODO

## Persistence Setup

The persistence is set up in `cz.cvut.kbss.jopa.eswc2016.persistence.PersistenceFactory`. Sesame native storage location is specified
in `config.properties` in `src/main/resources`. Note that the path expects that the application is running on Linux. Feel free to specify
a different storage location (including remote repositories, just remember to adhere to the Sesame repository naming scheme).

## Running the Demo

This demo is a web application with UI written in Javascript (using ReactJS). To run the application, run `mvn package` and deploy
the resulting war file into any Java EE 7 Web Profile-compliant server (Apache Tomcat, Jetty, Glassfish etc.).
The UI will use a prebuilt file `src/main/webapp/js/bundle.js`.

In order to modify the application, NodeJS has to be installed on the system. Go to `src/main/webapp` and run `npm install` to install the JS
dependencies. By executing `npm start`, a watcher will be started which rebuilds the UI archive whenever a change is made to the JS files.

The web UI is built with [ReactJS](https://facebook.github.io/react/) and uses its [Flux](https://facebook.github.io/flux/docs/overview.html)
architecture (modified version, framework [Reflux](https://github.com/reflux/refluxjs)). 
Individual JS files contain some additional explanatory comments.

#### TODO

- Web application (ReactJS) with Spring
- Querying (SPARQL-DL)  **OK**
- Inferred attributes, cascading, participation constraints, contexts   **OK**
- OWL2Java
- Storage switch    **OK**
- Properties     **OK**
- Types
- Possible to view raw storage content in UI    **OK**
- Possible to add/remove unmapped properties in UI  **OK**
- Air safety domain     **OK**
