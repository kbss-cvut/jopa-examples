/**
 * Copyright (C) 2019 Czech Technical University in Prague
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
    private OWLOntology ontology;
    private String repoUrl;

    public OWLOntologyManager getManager() {
        return manager;
    }

    public String getData() {
        try {
            // Load on every call, so that we have up-to-date data
            if (ontology != null) {
                manager.removeOntology(ontology);
            }
            ontology = manager.loadOntologyFromOntologyDocument(IRI.create(repoUrl));
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
