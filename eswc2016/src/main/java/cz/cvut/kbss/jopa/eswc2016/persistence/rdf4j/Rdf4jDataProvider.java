/*
 * JOPA Examples
 * Copyright (C) 2024 Czech Technical University in Prague
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package cz.cvut.kbss.jopa.eswc2016.persistence.rdf4j;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.config.RepositoryConfigException;
import org.eclipse.rdf4j.repository.manager.RepositoryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:rdf4j.properties")
public class Rdf4jDataProvider {

    private static final Logger LOG = LoggerFactory.getLogger(Rdf4jDataProvider.class);
    private static final String URL_PROPERTY = "rdf4j.repositoryUrl";

    @Autowired
    private Environment environment;

    @Autowired
    @Qualifier("rdf4jEMF")
    private EntityManagerFactory emf;

    private Repository repository;

    @Bean
    public Repository repository() {
        return repository;
    }

    @PostConstruct
    private void initializeStorage() {
        forceRepoInitialization();
        final String repoUrl = environment.getProperty(URL_PROPERTY);
        try {
            this.repository = RepositoryProvider.getRepository(repoUrl);
            assert repository.isInitialized();
        } catch (RepositoryException | RepositoryConfigException e) {
            LOG.error("Unable to connect to RDF4J repository at " + repoUrl, e);
        }
    }

    /**
     * Force JOPA to initialize the storage so that we don't have to initialize it ourselves.
     * <p/>
     * If we were to initialize the storage, we would have to create appropriate {@link
     * org.eclipse.rdf4j.repository.config.RepositoryConfig} for the repo, so we rather let JOPA do it for us.
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
