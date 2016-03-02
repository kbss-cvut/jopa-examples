package cz.cvut.kbss.jopa.eswc2016.rest;

import cz.cvut.kbss.jopa.eswc2016.config.ConfigurationService;
import cz.cvut.kbss.jopa.eswc2016.util.ConfigParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController extends BaseController {

    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping(method = RequestMethod.GET)
    public String getConfiguration(@RequestParam("key") String key) {
        final Object value = configurationService.get(ConfigParam.fromString(key));
        return value != null ? value.toString() : "";
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void set(@RequestParam Map<String, String> params) {
        params.forEach((key, value) -> configurationService.set(ConfigParam.fromString(key), value));
    }
}
