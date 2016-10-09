package cz.cvut.kbss.jopa.jsonld.environment;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "cz.cvut.kbss.jopa.jsonld.service")
public class TestServiceConfig {
}
