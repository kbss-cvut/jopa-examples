package cz.cvut.kbss.jopa.eswc2016.util;

import java.net.URI;

public abstract class Constants {

    private Constants() {
        throw new AssertionError();
    }

    /**
     * URI of context in which persons are stored.
     */
    public static final URI PERSONS_CONTEXT = URI.create("http://krizik.felk.cvut.cz/ontologies/eswc2016/persons");

    /**
     * Default user's username.
     */
    public static final String USERNAME = "master-chief";

    /**
     * Default ontology language.
     */
    public static final String LANGUAGE = "en";

    /**
     * Repository URL (physical location) configuration property.
     */
    public static final String URL_PROPERTY = "repositoryUrl";

    /**
     * OntoDriver datasource class configuration property.
     */
    public static final String DRIVER_PROPERTY = "driver";

    /**
     * Ontology logical URI configuration property.
     */
    public static final String ONTOLOGY_URI_PROPERTY = "ontologyUri";
}
