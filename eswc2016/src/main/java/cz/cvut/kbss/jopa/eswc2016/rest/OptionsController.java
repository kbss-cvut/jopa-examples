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
