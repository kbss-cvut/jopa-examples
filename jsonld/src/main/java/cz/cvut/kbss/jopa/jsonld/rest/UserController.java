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
package cz.cvut.kbss.jopa.jsonld.rest;

import cz.cvut.kbss.jopa.jsonld.dto.UserDto;
import cz.cvut.kbss.jopa.jsonld.dto.mapper.DtoMapper;
import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.jsonld.rest.exception.NotFoundException;
import cz.cvut.kbss.jopa.jsonld.service.UserService;
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
@RequestMapping("${apiPrefix}/users")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final DtoMapper dtoMapper;

    @Autowired
    public UserController(UserService userService, DtoMapper dtoMapper) {
        this.userService = userService;
        this.dtoMapper = dtoMapper;
    }

    @RequestMapping(method = RequestMethod.GET, produces = {JsonLd.MEDIA_TYPE, MediaType.APPLICATION_JSON_VALUE})
    public List<UserDto> getUsers() {
        return userService.findAll().stream().map(dtoMapper::userToDto).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET, produces = {JsonLd.MEDIA_TYPE,
                                                                              MediaType.APPLICATION_JSON_VALUE})
    public UserDto getUser(@PathVariable(name = "key") String key) {
        return dtoMapper.userToDto(findByKey(key));
    }

    private User findByKey(String key) {
        final User user = userService.findByKey(key);
        if (user == null) {
            throw NotFoundException.create("User", key);
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {JsonLd.MEDIA_TYPE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> createUser(@RequestBody UserDto dto) {
        final User user = dtoMapper.dtoToUser(dto);
        userService.persist(user);
        LOG.trace("User {} successfully persisted.", user);
        final String key = user.getKey();
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{key}", key);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.PUT, consumes = {JsonLd.MEDIA_TYPE,
                                                                              MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable(name = "key") String key, @RequestBody UserDto dto) {
        if (!key.equals(dto.getKey())) {
            throw new IllegalArgumentException("The passed user's key is different from the specified one.");
        }
        findByKey(key);  // Check that the user exists
        userService.update(dtoMapper.dtoToUser(dto));
        LOG.trace("User {} successfully updated.", dto);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(name = "key") String key) {
        final User user = findByKey(key);
        userService.remove(user);
        LOG.trace("User {} removed.", user);
    }
}
