package cz.cvut.kbss.jopa.eswc2016.persistence;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import cz.cvut.kbss.jopa.Persistence;
import cz.cvut.kbss.jopa.eswc2016.util.Constants;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProperties;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProvider;
import cz.cvut.kbss.ontodriver.OntoDriverProperties;
import cz.cvut.kbss.ontodriver.owlapi.config.OwlapiOntoDriverProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:owlapi.properties")
public class OwlapiPersistenceFactory {

    private static final String ONTOLOGY_URI_PARAM = "owlapi.ontologyUri";
    private static final String URL_PROPERTY = "owlapi.repositoryUrl";
    private static final String DRIVER_PROPERTY = "owlapi.driver";
    private static final String MAPPING_FILE = "owlapi.mappingFile";

    private static final Map<String, String> PARAMS = initParams();

    @Autowired
    private Environment environment;

    private EntityManagerFactory emf;

    @Bean(name = "owlapiEMF")
    public EntityManagerFactory entityManagerFactory() {
        return emf;
    }

    @PostConstruct
    private void init() {
        final Map<String, String> properties = new HashMap<>(PARAMS);
        // Logical ontology URI, required by OWLAPI
        properties.put(JOPAPersistenceProperties.ONTOLOGY_URI_KEY, environment.getProperty(ONTOLOGY_URI_PARAM));
        properties.put(JOPAPersistenceProperties.ONTOLOGY_PHYSICAL_URI_KEY, environment.getProperty(URL_PROPERTY));
        properties.put(JOPAPersistenceProperties.DATA_SOURCE_CLASS, environment.getProperty(DRIVER_PROPERTY));
        properties.put(OwlapiOntoDriverProperties.MAPPING_FILE_LOCATION, environment.getProperty(MAPPING_FILE));
        this.emf = Persistence.createEntityManagerFactory("eswc2016-owlapi", properties);
    }

    @PreDestroy
    private void close() {
        emf.close();
    }

    private static Map<String, String> initParams() {
        final Map<String, String> params = new HashMap<>();
        // Use Pellet for inference
        params.put(OntoDriverProperties.OWLAPI_REASONER_FACTORY_CLASS, PelletReasonerFactory.class.getName());
        // View transactional changes during transaction
        params.put(OntoDriverProperties.USE_TRANSACTIONAL_ONTOLOGY, Boolean.TRUE.toString());
        params.put(OntoDriverProperties.ONTOLOGY_LANGUAGE, Constants.LANGUAGE);
        params.put(JOPAPersistenceProperties.SCAN_PACKAGE, "cz.cvut.kbss.jopa.eswc2016.model");
        params.put(JOPAPersistenceProperties.JPA_PERSISTENCE_PROVIDER, JOPAPersistenceProvider.class.getName());
        return params;
    }
}
