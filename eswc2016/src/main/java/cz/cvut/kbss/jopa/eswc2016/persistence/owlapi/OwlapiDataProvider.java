package cz.cvut.kbss.jopa.eswc2016.persistence.owlapi;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;

@Component
@PropertySource("classpath:owlapi.properties")
public class OwlapiDataProvider {

    private static final Logger LOG = LoggerFactory.getLogger(OwlapiDataProvider.class);
    private static final String URL_PROPERTY = "owlapi.repositoryUrl";

    @Autowired
    private Environment environment;

    @Autowired
    @Qualifier("owlapiEMF")
    private EntityManagerFactory emf;

    private OWLOntologyManager manager;
    private String repoUrl;

    public OWLOntologyManager getManager() {
        return manager;
    }

    public String getData() {
        try {
            // Load on every call, so that we have up-to-date data
            final OWLOntology ontology = manager.loadOntologyFromOntologyDocument(IRI.create(repoUrl));
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            manager.saveOntology(ontology, bos);
            return bos.toString();
        } catch (OWLOntologyCreationException e) {
            LOG.error("Unable to load OWL API data.", e);
            return "";
        } catch (OWLOntologyStorageException e) {
            LOG.error("Unable to export OWL API data.", e);
            return "";
        }
    }

    @PostConstruct
    private void initializeStorage() {
        forceRepoInitialization();
        this.repoUrl = environment.getProperty(URL_PROPERTY);
        this.manager = OWLManager.createOWLOntologyManager();

    }

    /**
     * Force JOPA to initialize the storage so that we don't have to initialize it ourselves.
     * <p/>
     * If we were to initialize the storage, we would have to create appropriate {@link
     * org.openrdf.repository.config.RepositoryConfig} for the repo, so we rather let JOPA do it for us.
     */
    private void forceRepoInitialization() {
        final EntityManager em = emf.createEntityManager();
        try {
            // The URI doesn't matter, we just need to trigger repository connection initialization
            em.createNativeQuery("ASK { ?x a <http://example.org> . }", Boolean.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}
