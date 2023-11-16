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
package cz.cvut.kbss.jopa.eswc2016.rest;

import cz.cvut.kbss.jopa.eswc2016.model.dto.RawJson;
import cz.cvut.kbss.jopa.eswc2016.service.PropertyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/options")
public class OptionsController extends BaseController {

    @Autowired
    private PropertyLoader propertyLoader;

    /**
     * Loads properties present in the documentation ontology.
     *
     * @return List of properties in JSON-LD
     */
    @RequestMapping(method = RequestMethod.GET, value = "/properties", produces = MediaType.APPLICATION_JSON_VALUE)
    public RawJson getProperties() {
        return new RawJson(propertyLoader.loadProperties());
    }
}
