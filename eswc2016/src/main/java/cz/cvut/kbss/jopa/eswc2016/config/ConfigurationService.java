/*
 * JOPA Examples
 * Copyright (C) 2023 Czech Technical University in Prague
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
package cz.cvut.kbss.jopa.eswc2016.config;

import cz.cvut.kbss.jopa.eswc2016.util.ConfigParam;
import cz.cvut.kbss.jopa.eswc2016.util.RepositoryType;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationService {

    private final Map<ConfigParam, String> configuration = new ConcurrentHashMap<>();

    public void set(ConfigParam param, String value) {
        configuration.put(param, value);
    }

    public String get(ConfigParam param) {
        return configuration.get(param);
    }

    @PostConstruct
    private void init() {
        configuration.put(ConfigParam.REPOSITORY_TYPE, RepositoryType.RDF4J.toString());
    }
}
