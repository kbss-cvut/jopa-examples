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
package cz.cvut.kbss.jopa.jsonld.rest;

import cz.cvut.kbss.jopa.jsonld.dto.OrganizationDto;
import cz.cvut.kbss.jopa.jsonld.dto.mapper.DtoMapper;
import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.jsonld.rest.exception.NotFoundException;
import cz.cvut.kbss.jopa.jsonld.service.OrganizationService;
import cz.cvut.kbss.jsonld.JsonLd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${apiPrefix}/organizations")
public class OrganizationController {

    private static final Logger LOG = LoggerFactory.getLogger(OrganizationController.class);

    private final OrganizationService organizationService;

    private final DtoMapper dtoMapper;

    @Autowired
    public OrganizationController(OrganizationService organizationService, DtoMapper dtoMapper) {
        this.organizationService = organizationService;
        this.dtoMapper = dtoMapper;
    }

    @RequestMapping(method = RequestMethod.GET, produces = {JsonLd.MEDIA_TYPE, MediaType.APPLICATION_JSON_VALUE})
    public List<OrganizationDto> getOrganizations() {
        return organizationService.findAll().stream().map(dtoMapper::organizationToDto).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET, produces = {JsonLd.MEDIA_TYPE,
                                                                              MediaType.APPLICATION_JSON_VALUE})
    public OrganizationDto getOrganization(@PathVariable(name = "key") String key) {
        return dtoMapper.organizationToDto(findByKey(key));
    }

    private Organization findByKey(String key) {
        final Organization organization = organizationService.findByKey(key);
        if (organization == null) {
            throw NotFoundException.create("Organization", key);
        }
        return organization;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {JsonLd.MEDIA_TYPE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> createOrganization(@RequestBody OrganizationDto data) {
        final Organization organization = dtoMapper.dtoToOrganization(data);
        organizationService.persist(organization);
        LOG.trace("Organization {} successfully persisted.", organization);
        final String key = organization.getKey();
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{key}", key);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.PUT, consumes = {JsonLd.MEDIA_TYPE,
                                                                              MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrganization(@PathVariable(name = "key") String key, @RequestBody OrganizationDto organization) {
        if (!key.equals(organization.getKey())) {
            throw new IllegalArgumentException("The passed organization's key is different from the specified one.");
        }
        getOrganization(key);  // Check that the organization exists
        organizationService.update(dtoMapper.dtoToOrganization(organization));
        LOG.trace("Organization {} successfully updated.", organization);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable(name = "key") String key) {
        final Organization organization = findByKey(key);
        organizationService.remove(organization);
        LOG.trace("Organization {} removed.", organization);
    }
}
