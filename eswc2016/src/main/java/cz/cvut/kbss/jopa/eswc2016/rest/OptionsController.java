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
