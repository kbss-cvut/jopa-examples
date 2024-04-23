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

import cz.cvut.kbss.jopa.eswc2016.persistence.BasePersistenceFactory;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProperties;
import cz.cvut.kbss.ontodriver.rdf4j.config.Rdf4jOntoDriverProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides entity manager factory as a Spring bean.
 * <p/>
 * This class provides access to RDF4J persistence.
 */
@Configuration
@PropertySource("classpath:rdf4j.properties")
public class Rdf4jPersistenceFactory extends BasePersistenceFactory {

    private static final String URL_PROPERTY = "rdf4j.repositoryUrl";
    private static final String DRIVER_PROPERTY = "rdf4j.driver";

    @Bean(name = "rdf4jEMF")
    public EntityManagerFactory entityManagerFactory() {
        return getEmf();
    }

    @Override
    protected String getType() {
        return "rdf4j";
    }

    @Override
    protected Map<String, String> getProperties() {
        final Map<String, String> properties = new HashMap<>();
        properties.put(JOPAPersistenceProperties.ONTOLOGY_PHYSICAL_URI_KEY, environment.getProperty(URL_PROPERTY));
        properties.put(JOPAPersistenceProperties.DATA_SOURCE_CLASS, environment.getProperty(DRIVER_PROPERTY));
        properties.put(Rdf4jOntoDriverProperties.USE_INFERENCE, Boolean.TRUE.toString());
        return properties;
    }
}
