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
        params.put(JOPAPersistenceProperties.LANG, Constants.LANGUAGE);
        params.put(JOPAPersistenceProperties.SCAN_PACKAGE, "cz.cvut.kbss.jopa.eswc2016.model");
        params.put(JOPAPersistenceProperties.JPA_PERSISTENCE_PROVIDER, JOPAPersistenceProvider.class.getName());
        return params;
    }

    protected abstract String getType();

    protected abstract Map<String, String> getProperties();
}
