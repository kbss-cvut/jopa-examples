package cz.cvut.kbss.jopa.eswc2016.persistence;

import cz.cvut.kbss.jopa.Persistence;
import cz.cvut.kbss.jopa.eswc2016.util.Constants;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProperties;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProvider;
import cz.cvut.kbss.ontodriver.config.OntoDriverProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

/**
 * Common persistence configuration. Subclasses provide storage-specific configuration.
 */
public abstract class BasePersistenceFactory {

    @Autowired
    protected Environment environment;

    private EntityManagerFactory emf;

    protected EntityManagerFactory getEmf() {
        return emf;
    }

    @PostConstruct
    private void init() {
        final Map<String, String> properties = new HashMap<>(initParams());
        properties.putAll(getProperties());
        this.emf = Persistence.createEntityManagerFactory("eswc2016-" + getType(), properties);
    }

    @PreDestroy
    private void close() {
        emf.close();
    }

    private static Map<String, String> initParams() {
        final Map<String, String> params = new HashMap<>();
        // View transactional changes during transaction
        params.put(OntoDriverProperties.USE_TRANSACTIONAL_ONTOLOGY, Boolean.TRUE.toString());
        params.put(OntoDriverProperties.ONTOLOGY_LANGUAGE, Constants.LANGUAGE);
        params.put(JOPAPersistenceProperties.SCAN_PACKAGE, "cz.cvut.kbss.jopa.eswc2016.model");
        params.put(JOPAPersistenceProperties.JPA_PERSISTENCE_PROVIDER, JOPAPersistenceProvider.class.getName());
        return params;
    }

    protected abstract String getType();

    protected abstract Map<String, String> getProperties();
}
