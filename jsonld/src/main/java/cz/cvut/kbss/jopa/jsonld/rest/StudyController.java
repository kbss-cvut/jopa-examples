package cz.cvut.kbss.jopa.jsonld.rest;

import cz.cvut.kbss.jopa.jsonld.model.Study;
import cz.cvut.kbss.jopa.jsonld.rest.exception.NotFoundException;
import cz.cvut.kbss.jopa.jsonld.service.StudyService;
import cz.cvut.kbss.jsonld.JsonLd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studies")
public class StudyController {

    private static final Logger LOG = LoggerFactory.getLogger(StudyController.class);

    private final StudyService studyService;

    @Autowired
    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = JsonLd.MEDIA_TYPE)
    public List<Study> getStudies() {
        return studyService.findAll();
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET, produces = JsonLd.MEDIA_TYPE)
    public Study getStudy(@PathVariable(name = "key") String key) {
        final Study study = studyService.findByKey(key);
        if (study == null) {
            throw NotFoundException.create("Study", key);
        }
        return study;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = JsonLd.MEDIA_TYPE)
    public ResponseEntity<Void> createStudy(@RequestBody Study study) {
        studyService.persist(study);
        LOG.trace("Study {} successfully persisted.", study);
        final String key = study.getKey();
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{key}", key);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.PUT, consumes = JsonLd.MEDIA_TYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStudy(@PathVariable(name = "key") String key, @RequestBody Study study) {
        if (!key.equals(study.getKey())) {
            throw new IllegalArgumentException("The passed study's key is different from the specified one.");
        }
        getStudy(key);  // Check that the study exists
        studyService.update(study);
        LOG.trace("Study {} successfully updated.", study);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudy(@PathVariable(name = "key") String key) {
        final Study study = getStudy(key);
        studyService.remove(study);
        LOG.trace("Study {} removed.", study);
    }
}
