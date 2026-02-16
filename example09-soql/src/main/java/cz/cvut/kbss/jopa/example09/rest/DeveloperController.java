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
package cz.cvut.kbss.jopa.example09.rest;

import cz.cvut.kbss.jopa.example09.model.Developer;
import cz.cvut.kbss.jopa.example09.model.Game;
import cz.cvut.kbss.jopa.example09.persistence.DeveloperRepositories;
import cz.cvut.kbss.jopa.example09.persistence.GameRepositories;
import cz.cvut.kbss.jopa.example09.persistence.QueryMechanism;
import cz.cvut.kbss.jsonld.JsonLd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    private final DeveloperRepositories developerRepository;
    private final GameRepositories gameRepository;

    @Autowired
    public DeveloperController(DeveloperRepositories developerRepository, GameRepositories gameRepository) {
        this.developerRepository = developerRepository;
        this.gameRepository = gameRepository;
    }

    @RequestMapping(produces = JsonLd.MEDIA_TYPE)
    public List<Developer> getDevelopers(@RequestParam(name = "name", required = false) String name,
                                         @RequestParam(name = "queryMechanism", required = false) String queryMechanism) {
        if (name != null) {
            return developerRepository.findByName(name, QueryMechanism.fromString(queryMechanism));
        }
        return developerRepository.findAll(QueryMechanism.fromString(queryMechanism));
    }

    @RequestMapping(value = "/small", produces = JsonLd.MEDIA_TYPE)
    public List<Developer> getSmallDevelopers(
            @RequestParam(name = "queryMechanism", required = false) String queryMechanism) {
        return developerRepository.findSmallDevelopers(QueryMechanism.fromString(queryMechanism));
    }

    @RequestMapping(value = "/small/games", produces = JsonLd.MEDIA_TYPE)
    public List<Game> getGamesBySmallDevelopers(
            @RequestParam(name = "queryMechanism", required = false) String queryMechanism) {
        return gameRepository.findAllBySmallDevelopers(QueryMechanism.fromString(queryMechanism));
    }

    @RequestMapping(value = "/{localName}", produces = JsonLd.MEDIA_TYPE)
    public Developer getDeveloper(@PathVariable String localName,
                                  @RequestParam(name = "queryMechanism", required = false) String queryMechanism) {
        return developerRepository.findById(localName, QueryMechanism.fromString(queryMechanism)).orElseThrow(
                () -> new NotFoundException("Developer with name " + localName + " not found!"));
    }

    @RequestMapping(value = "/{localName}/games", produces = JsonLd.MEDIA_TYPE)
    public List<Game> getDevelopersGames(@PathVariable String localName,
                                         @RequestParam(name = "queryMechanism", required = false) String queryMechanism) {
        final Developer dev = getDeveloper(localName, queryMechanism);
        return gameRepository.findAll(dev, QueryMechanism.fromString(queryMechanism));
    }
}
