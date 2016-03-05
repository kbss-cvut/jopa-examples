package cz.cvut.kbss.jopa.eswc2016.service;

import cz.cvut.kbss.jopa.eswc2016.rest.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

@Service
public class PropertiesValidationService {

    public void validate(Map<String, Set<String>> properties) {
        if (properties == null || properties.isEmpty()) {
            return;
        }
        for (String property : properties.keySet()) {
            try {
                new URI(property);
            } catch (URISyntaxException e) {
                throw new ValidationException("Property " + property + " is not a valid URI.");
            }
        }
    }
}
