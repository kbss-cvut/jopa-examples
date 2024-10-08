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
import cz.cvut.kbss.jopa.example09.persistence.DeveloperRepository;
import cz.cvut.kbss.jopa.example09.persistence.GameRepository;
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

    private final DeveloperRepository developerRepository;
    private final GameRepository gameRepository;

    @Autowired
    public DeveloperController(DeveloperRepository developerRepository, GameRepository gameRepository) {
        this.developerRepository = developerRepository;
        this.gameRepository = gameRepository;
    }

    @RequestMapping(produces = JsonLd.MEDIA_TYPE)
    public List<Developer> getDevelopers(@RequestParam(name = "name", required = false) String name) {
        if (name != null) {
            return developerRepository.findByName(name);
        }
        return developerRepository.findAll();
    }

    @RequestMapping(value = "/small", produces = JsonLd.MEDIA_TYPE)
    public List<Developer> getSmallDevelopers() {
        return developerRepository.findSmallDevelopers();
    }

    @RequestMapping(value = "/small/games", produces = JsonLd.MEDIA_TYPE)
    public List<Game> getGamesBySmallDevelopers() {
        return gameRepository.findAllBySmallDevelopers();
    }

    @RequestMapping(value = "/{localName}", produces = JsonLd.MEDIA_TYPE)
    public Developer getDeveloper(@PathVariable String localName) {
        return developerRepository.findById(localName).orElseThrow(
                () -> new NotFoundException("Developer with name " + localName + " not found!"));
    }

    @RequestMapping(value = "/{localName}/games", produces = JsonLd.MEDIA_TYPE)
    public List<Game> getDevelopersGames(@PathVariable String localName) {
        final Developer dev = getDeveloper(localName);
        return gameRepository.findAll(dev);
    }
}
