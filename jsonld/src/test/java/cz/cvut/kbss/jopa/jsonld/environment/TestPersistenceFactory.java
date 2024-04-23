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
package cz.cvut.kbss.jopa.jsonld.environment;

import cz.cvut.kbss.jopa.Persistence;
import cz.cvut.kbss.jopa.jsonld.persistence.PersistenceFactory;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProperties;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProvider;
import cz.cvut.kbss.ontodriver.rdf4j.config.Rdf4jOntoDriverProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:config.properties")
public class TestPersistenceFactory {

    @Autowired
    private Environment environment;

    private EntityManagerFactory emf;

    @Bean
    @Primary
    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    @PostConstruct
    private void init() {
        final Map<String, String> properties = getDefaultProperties();
        properties.put(JOPAPersistenceProperties.ONTOLOGY_PHYSICAL_URI_KEY,
                environment.getProperty(PersistenceFactory.URL_PROPERTY));
        properties.put(JOPAPersistenceProperties.DATA_SOURCE_CLASS,
                environment.getProperty(PersistenceFactory.DRIVER_PROPERTY));
        this.emf = Persistence.createEntityManagerFactory("jsonldTestPU", properties);
    }

    @PreDestroy
    private void close() {
        emf.close();
    }

    static Map<String, String> getDefaultProperties() {
        final Map<String, String> properties = new HashMap<>();
        properties.put(JOPAPersistenceProperties.LANG, "en");
        properties.put(JOPAPersistenceProperties.SCAN_PACKAGE, "cz.cvut.kbss.jopa.jsonld.model");
        properties.put(Rdf4jOntoDriverProperties.USE_VOLATILE_STORAGE, Boolean.TRUE.toString());
        properties.put(Rdf4jOntoDriverProperties.USE_INFERENCE, Boolean.FALSE.toString());
        properties.put(JOPAPersistenceProperties.JPA_PERSISTENCE_PROVIDER, JOPAPersistenceProvider.class.getName());
        return properties;
    }
}
