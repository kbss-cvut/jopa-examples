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
package cz.cvut.kbss.jopa.eswc2016.service;

import cz.cvut.kbss.jopa.eswc2016.rest.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

@Service
public class PropertiesValidationService {

    public void validate(Map<String, Set<String>> properties) {
        if (properties == null || properties.isEmpty()) {
            return;
        }
        for (String property : properties.keySet()) {
            try {
                new URI(property);
            } catch (URISyntaxException e) {
                throw new ValidationException("Property " + property + " is not a valid URI.");
            }
        }
    }
}
