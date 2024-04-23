package cz.cvut.kbss.jopa.example10;

import cz.cvut.kbss.jopa.Persistence;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProperties;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProvider;
import cz.cvut.kbss.ontodriver.rdf4j.Rdf4jDataSource;
import cz.cvut.kbss.ontodriver.rdf4j.config.Rdf4jOntoDriverProperties;

import java.util.HashMap;
import java.util.Map;

public class PersistenceFactory {

    public static final String LANGUAGE = "en";

    private PersistenceFactory() {
        throw new AssertionError();
    }

    public static EntityManagerFactory createPersistenceUnitWithGlobalLanguage() {
        final Map<String, String> props = commonProperties("Example10-WithGlobalLanguage");
        // Ontology language
        props.put(JOPAPersistenceProperties.LANG, LANGUAGE);
        return Persistence.createEntityManagerFactory("Example10-LanguagePU", props);
    }

    private static Map<String, String> commonProperties(String repoName) {
        final Map<String, String> props = new HashMap<>();
        props.put(JOPAPersistenceProperties.ONTOLOGY_PHYSICAL_URI_KEY, repoName);
        props.put(JOPAPersistenceProperties.DATA_SOURCE_CLASS, Rdf4jDataSource.class.getName());
        // Use in-memory storage if not remote or local file path specified
        props.put(Rdf4jOntoDriverProperties.USE_VOLATILE_STORAGE, Boolean.TRUE.toString());
        // Persistence provider name
        props.put(JOPAPersistenceProperties.JPA_PERSISTENCE_PROVIDER, JOPAPersistenceProvider.class.getName());
        props.put(JOPAPersistenceProperties.SCAN_PACKAGE, "cz.cvut.kbss.jopa.example10.model");
        return props;
    }

    public static EntityManagerFactory createPersistenceUnitWithoutGlobalLanguage() {
        final Map<String, String> props = commonProperties("Example10-WithoutGlobalLanguage");
        return Persistence.createEntityManagerFactory("Example10-LanguageLessPU", props);
    }
}
