package cz.cvut.kbss.jopa.jsonld.rest;

import cz.cvut.kbss.jsonld.JsonLd;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(method = RequestMethod.GET, produces = JsonLd.MEDIA_TYPE)
    public String sayHello(@RequestParam(name = "name") String name) {
        return "Hello " + name;
    }
}
