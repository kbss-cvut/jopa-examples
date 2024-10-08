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
package cz.cvut.kbss.jopa.eswc2016.rest;

import cz.cvut.kbss.jopa.eswc2016.config.ConfigurationService;
import cz.cvut.kbss.jopa.eswc2016.util.ConfigParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController extends BaseController {

    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping(method = RequestMethod.GET)
    public String getConfiguration(@RequestParam("key") String key) {
        final Object value = configurationService.get(ConfigParam.fromString(key));
        return value != null ? value.toString() : "";
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void set(@RequestParam Map<String, String> params) {
        params.forEach((key, value) -> configurationService.set(ConfigParam.fromString(key), value));
    }
}
