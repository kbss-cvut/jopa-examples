package cz.cvut.kbss.jopa.example08.persistence;

import cz.cvut.kbss.jopa.Persistence;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProperties;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProvider;
import cz.cvut.kbss.ontodriver.config.OntoDriverProperties;
import cz.cvut.kbss.ontodriver.jena.JenaDataSource;
import cz.cvut.kbss.ontodriver.jena.config.JenaOntoDriverProperties;
import org.apache.jena.reasoner.rulesys.RDFSRuleReasonerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class PersistenceFactory {

    private static final Logger LOG = LoggerFactory.getLogger(PersistenceFactory.class);

    private final Environment environment;
    private final SchemaExporter schemaExporter;

    private EntityManagerFactory emf;

    @Autowired
    public PersistenceFactory(Environment environment, SchemaExporter schemaExporter) {
        this.environment = environment;
        this.schemaExporter = schemaExporter;
    }

    @PostConstruct
    private void init() {
        final String repoPath = environment.getProperty("repositoryUrl");
        LOG.info("Using repository path: {}.", repoPath);
        schemaExporter.exportSchema();

        final Map<String, String> props = new HashMap<>();
        // Here we set up basic storage access properties - driver class, physical location of the storage
        props.put(JOPAPersistenceProperties.ONTOLOGY_PHYSICAL_URI_KEY, repoPath);
        props.put(JOPAPersistenceProperties.DATA_SOURCE_CLASS, JenaDataSource.class.getName());
        // Let's use Jena TDB for storage
        props.put(JenaOntoDriverProperties.JENA_STORAGE_TYPE, JenaOntoDriverProperties.TDB);
        // Use Jena's rule-based RDFS reasoner
        props.put(OntoDriverProperties.REASONER_FACTORY_CLASS, RDFSRuleReasonerFactory.class.getName());
        // View transactional changes during transaction
        props.put(OntoDriverProperties.USE_TRANSACTIONAL_ONTOLOGY, Boolean.TRUE.toString());
        // Where to look for entities
        props.put(JOPAPersistenceProperties.SCAN_PACKAGE, "cz.cvut.kbss.jopa.example08.model");
        // Ontology language
        props.put(JOPAPersistenceProperties.LANG, "en");
        // Persistence provider name
        props.put(JOPAPersistenceProperties.JPA_PERSISTENCE_PROVIDER, JOPAPersistenceProvider.class.getName());

        this.emf = Persistence.createEntityManagerFactory("jopaExample08PU", props);
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return emf;
    }

    @PreDestroy
    public void close() {
        emf.close();
    }
}
