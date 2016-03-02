package cz.cvut.kbss.jopa.eswc2016.persistence;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProperties;
import cz.cvut.kbss.ontodriver.OntoDriverProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:owlapi.properties")
public class OwlapiPersistenceFactory extends PersistenceFactoryBase {

    private static final String ONTOLOGY_URI_PARAM = "ontologyUri";

    @Bean(name = "owlapiEMF")
    public EntityManagerFactory entityManagerFactory() {
        return getEmf();
    }

    @Override
    Map<String, String> getAdditionalParams() {
        final Map<String, String> params = new HashMap<>();
        // Logical ontology URI, required by OWLAPI
        params.put(JOPAPersistenceProperties.ONTOLOGY_URI_KEY, environment.getProperty(ONTOLOGY_URI_PARAM));
        // Use Pellet for inference
        params.put(OntoDriverProperties.OWLAPI_REASONER_FACTORY_CLASS, PelletReasonerFactory.class.getName());
        // View transactional changes during transaction
        params.put(OntoDriverProperties.USE_TRANSACTIONAL_ONTOLOGY, Boolean.TRUE.toString());
        return params;
    }

    @Override
    String getType() {
        return "owlapi";
    }
}
