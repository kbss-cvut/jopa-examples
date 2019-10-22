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
package cz.cvut.kbss.jopa.eswc2016.service;

import cz.cvut.kbss.jopa.eswc2016.rest.exception.WebServiceIntegrationException;
import cz.cvut.kbss.jopa.eswc2016.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

@Service
@PropertySource("classpath:config.properties")
public class PropertyLoader {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyLoader.class);

    private static final String REPOSITORY_CONFIG = "propertyRepositoryUrl";

    /**
     * The query to get properties from repo
     */
    private static final String QUERY = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n\n" +
            "SELECT DISTINCT ?p WHERE { ?p a rdf:Property . } ORDER BY ?p";

    @Autowired
    private Environment environment;

    /**
     * Loads properties from a remote repository.
     *
     * @return Loaded data
     */
    public String loadProperties() {
        final HttpHeaders headers = new HttpHeaders();
        // Set default accept type to JSON-LD
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        final String remoteUrl = environment.getProperty(REPOSITORY_CONFIG);
        if (remoteUrl == null || remoteUrl.isEmpty()) {
            LOG.warn("Property repository URL not specified. Properties will not be available.");
            return "";
        }
        final URI urlWithQuery = prepareUri(remoteUrl);
        final HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        final RestTemplate restTemplate = new RestTemplate();
        if (LOG.isTraceEnabled()) {
            LOG.trace("Getting remote data using {}", urlWithQuery.toString());
        }
        try {
            final ResponseEntity<String> result = restTemplate
                    .exchange(urlWithQuery, HttpMethod.GET, entity, String.class);
            return result.getBody();
        } catch (Exception e) {
            LOG.error("Error when requesting remote data, url: " + urlWithQuery.toString(), e);
            throw new WebServiceIntegrationException("Unable to fetch remote data.", e);
        }
    }

    private URI prepareUri(String remoteUrl) {
        try {
            final String encodedQuery = URLEncoder.encode(QUERY, Constants.UTF_8_ENCODING);
            return URI.create(remoteUrl + "?query=" + encodedQuery);
        } catch (UnsupportedEncodingException e) {
            throw new WebServiceIntegrationException("Unable to encode query.", e);
        }
    }
}
