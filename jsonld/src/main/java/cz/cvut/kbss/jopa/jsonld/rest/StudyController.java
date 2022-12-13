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
package cz.cvut.kbss.jopa.jsonld.rest;

import cz.cvut.kbss.jopa.jsonld.dto.StudyDto;
import cz.cvut.kbss.jopa.jsonld.dto.mapper.DtoMapper;
import cz.cvut.kbss.jopa.jsonld.model.Study;
import cz.cvut.kbss.jopa.jsonld.rest.exception.NotFoundException;
import cz.cvut.kbss.jopa.jsonld.service.StudyService;
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
@RequestMapping("${apiPrefix}/studies")
public class StudyController {

    private static final Logger LOG = LoggerFactory.getLogger(StudyController.class);

    private final StudyService studyService;

    private final DtoMapper dtoMapper;

    @Autowired
    public StudyController(StudyService studyService, DtoMapper dtoMapper) {
        this.studyService = studyService;
        this.dtoMapper = dtoMapper;
    }

    @RequestMapping(method = RequestMethod.GET, produces = {JsonLd.MEDIA_TYPE, MediaType.APPLICATION_JSON_VALUE})
    public List<StudyDto> getStudies() {
        return studyService.findAll().stream().map(dtoMapper::studyToDto).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET, produces = {JsonLd.MEDIA_TYPE,
                                                                              MediaType.APPLICATION_JSON_VALUE})
    public StudyDto getStudy(@PathVariable(name = "key") String key) {
        return dtoMapper.studyToDto(findByKey(key));
    }

    private Study findByKey(String key) {
        final Study study = studyService.findByKey(key);
        if (study == null) {
            throw NotFoundException.create("Study", key);
        }
        return study;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {JsonLd.MEDIA_TYPE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> createStudy(@RequestBody StudyDto dto) {
        final Study study = dtoMapper.dtoToStudy(dto);
        studyService.persist(study);
        LOG.trace("Study {} successfully persisted.", study);
        final String key = study.getKey();
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{key}", key);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.PUT, consumes = {JsonLd.MEDIA_TYPE,
                                                                              MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStudy(@PathVariable(name = "key") String key, @RequestBody StudyDto dto) {
        if (!key.equals(dto.getKey())) {
            throw new IllegalArgumentException("The passed study's key is different from the specified one.");
        }
        getStudy(key);  // Check that the study exists
        studyService.update(dtoMapper.dtoToStudy(dto));
        LOG.trace("Study {} successfully updated.", dto);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudy(@PathVariable(name = "key") String key) {
        final Study study = findByKey(key);
        studyService.remove(study);
        LOG.trace("Study {} removed.", study);
    }
}
