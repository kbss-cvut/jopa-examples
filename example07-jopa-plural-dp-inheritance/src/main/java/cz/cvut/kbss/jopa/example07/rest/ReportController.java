package cz.cvut.kbss.jopa.example07.rest;

import cz.cvut.kbss.jopa.example07.model.Report;
import cz.cvut.kbss.jopa.example07.rest.exception.NotFoundException;
import cz.cvut.kbss.jopa.example07.service.ReportService;
import cz.cvut.kbss.jsonld.JsonLd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = JsonLd.MEDIA_TYPE)
    public List<Report> getAll() {
        return reportService.findAll();
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET, produces = JsonLd.MEDIA_TYPE)
    public Report getByKey(@PathVariable("key") Integer key) {
        final Report report = reportService.find(key);
        if (report == null) {
            throw NotFoundException.create("Report", key);
        }
        return report;
    }
}
