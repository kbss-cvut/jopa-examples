package cz.cvut.kbss.jopa.eswc2016.persistence;

import cz.cvut.kbss.jopa.Persistence;
import cz.cvut.kbss.jopa.eswc2016.util.Constants;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProperties;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProvider;
import cz.cvut.kbss.ontodriver.OntoDriverProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Common persistence configuration.
 */
public abstract class PersistenceFactoryBase {

    static final Logger LOG = LoggerFactory.getLogger(PersistenceFactoryBase.class);

    static final String URL_PROPERTY = "repositoryUrl";
    static final String DRIVER_PROPERTY = "driver";

    static final Map<String, String> PARAMS = initParams();

    @Autowired
    Environment environment;

    private EntityManagerFactory emf;

    final EntityManagerFactory getEmf() {
        return emf;
    }

    @PostConstruct
    private void init() {
        final Map<String, String> properties = new HashMap<>(PARAMS);
        properties.putAll(getAdditionalParams());
        properties.put(JOPAPersistenceProperties.ONTOLOGY_PHYSICAL_URI_KEY, environment.getProperty(URL_PROPERTY));
        properties.put(JOPAPersistenceProperties.DATA_SOURCE_CLASS, environment.getProperty(DRIVER_PROPERTY));
        this.emf = Persistence.createEntityManagerFactory("eswc2016-" + getType(), properties);
    }

    @PreDestroy
    private void close() {
        emf.close();
    }

    private static Map<String, String> initParams() {
        final Map<String, String> map = new HashMap<>();
        map.put(OntoDriverProperties.ONTOLOGY_LANGUAGE, Constants.LANGUAGE);
        map.put(JOPAPersistenceProperties.SCAN_PACKAGE, "cz.cvut.kbss.jopa.eswc2016.model");
        map.put(JOPAPersistenceProperties.JPA_PERSISTENCE_PROVIDER, JOPAPersistenceProvider.class.getName());
        return Collections.unmodifiableMap(map);
    }

    abstract Map<String, String> getAdditionalParams();

    abstract String getType();
}
