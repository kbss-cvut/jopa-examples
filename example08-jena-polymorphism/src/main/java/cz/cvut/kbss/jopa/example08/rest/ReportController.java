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
package cz.cvut.kbss.jopa.example08.rest;

import cz.cvut.kbss.jopa.example08.model.AbstractReport;
import cz.cvut.kbss.jopa.example08.service.ReportService;
import cz.cvut.kbss.jsonld.JsonLd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private static final Logger LOG = LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = JsonLd.MEDIA_TYPE)
    public List<AbstractReport> findAll() {
        return reportService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{key}", produces = JsonLd.MEDIA_TYPE)
    public AbstractReport find(@PathVariable("key") String key) {
        return reportService.findByKey(key)
                            .orElseThrow(() -> new NotFoundException("Report with key " + key + " not found."));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = JsonLd.MEDIA_TYPE)
    public ResponseEntity<Void> create(@RequestBody AbstractReport report) {
        reportService.persist(report);
        LOG.debug("Report {} successfully persisted.", report.getUri());
        final HttpHeaders headers = createLocationHeaderFromCurrentUri("/{key}", report.getKey());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Creates HTTP headers object with a location header with the specified path appended to the current request URI.
     * <p>
     * The {@code uriVariableValues} are used to fill in possible variables specified in the {@code path}.
     *
     * @param path              Path to add to the current request URI in order to construct a resource location
     * @param uriVariableValues Values used to replace possible variables in the path
     * @return HttpHeaders with location headers set
     */
    private static HttpHeaders createLocationHeaderFromCurrentUri(String path, Object... uriVariableValues) {
        assert path != null;

        final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path(path).buildAndExpand(
                uriVariableValues).toUri();
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.LOCATION, location.toString());
        return headers;
    }
}
