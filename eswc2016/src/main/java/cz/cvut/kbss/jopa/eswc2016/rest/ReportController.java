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
package cz.cvut.kbss.jopa.eswc2016.rest;

import cz.cvut.kbss.jopa.eswc2016.model.dto.ReportDto;
import cz.cvut.kbss.jopa.eswc2016.model.model.Report;
import cz.cvut.kbss.jopa.eswc2016.rest.exception.NotFoundException;
import cz.cvut.kbss.jopa.eswc2016.rest.exception.ValidationException;
import cz.cvut.kbss.jopa.eswc2016.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController extends BaseController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReportDto> getReports() {
        return reportService.findAllDtos();
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Report getReport(@PathVariable("key") Long key) {
        return getReportInternal(key);
    }

    private Report getReportInternal(Long key) {
        final Report r = reportService.findByKey(key);
        if (r == null) {
            throw NotFoundException.create("Report", key);
        }
        return r;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createReport(@RequestBody Report report) {
        reportService.persist(report);
        if (LOG.isTraceEnabled()) {
            LOG.trace("Report {} successfully created.", report.getId());
        }
        final HttpHeaders header = RestUtils.createLocationHeader("/{key}", report.getIdentifier());
        return new ResponseEntity<>(header, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateReport(@PathVariable("key") Long key, @RequestBody Report report) {
        if (!key.equals(report.getIdentifier())) {
            throw new ValidationException("Report identifier and the path identifier do not match.");
        }
        reportService.update(report);
        if (LOG.isTraceEnabled()) {
            LOG.trace("Report {} successfully updated.", report);
        }
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReport(@PathVariable Long key) {
        final Report toDelete = getReportInternal(key);
        reportService.remove(toDelete);
        if (LOG.isTraceEnabled()) {
            LOG.trace("Report {} deleted.", toDelete);
        }
    }
}
