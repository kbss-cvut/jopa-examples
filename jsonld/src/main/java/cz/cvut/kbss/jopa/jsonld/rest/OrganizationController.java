package cz.cvut.kbss.jopa.jsonld.rest;

import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.jsonld.rest.exception.NotFoundException;
import cz.cvut.kbss.jopa.jsonld.service.OrganizationService;
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
@RequestMapping("/organizations")
public class OrganizationController {

    private static final Logger LOG = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(method = RequestMethod.GET, produces = JsonLd.MEDIA_TYPE)
    public List<Organization> getOrganizations() {
        return organizationService.findAll();
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET, produces = JsonLd.MEDIA_TYPE)
    public Organization getOrganization(@PathVariable(name = "key") String key) {
        final Organization organization = organizationService.findByKey(key);
        if (organization == null) {
            throw NotFoundException.create("Organization", key);
        }
        return organization;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = JsonLd.MEDIA_TYPE)
    public ResponseEntity<Void> createOrganization(@RequestBody Organization organization) {
        organizationService.persist(organization);
        LOG.trace("Organization {} successfully persisted.", organization);
        final String key = organization.getKey();
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{key}", key);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.PUT, consumes = JsonLd.MEDIA_TYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrganization(@PathVariable(name = "key") String key, @RequestBody Organization organization) {
        if (!key.equals(organization.getKey())) {
            throw new IllegalArgumentException("The passed organization's key is different from the specified one.");
        }
        getOrganization(key);  // Check that the organization exists
        organizationService.update(organization);
        LOG.trace("Organization {} successfully updated.", organization);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable(name = "key") String key) {
        final Organization organization = getOrganization(key);
        organizationService.remove(organization);
        LOG.trace("Organization {} removed.", organization);
    }
}
