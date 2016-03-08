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

    public static final String APPLICATION_JSON_LD_TYPE = "application/ld+json";

    public static final String UTF_8_ENCODING = "UTF-8";
}
