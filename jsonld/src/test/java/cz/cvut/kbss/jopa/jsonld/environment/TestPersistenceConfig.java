package cz.cvut.kbss.jopa.jsonld.environment;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"cz.cvut.kbss.jopa.jsonld.persistence"})
@Import({TestPersistenceFactory.class})
public class TestPersistenceConfig {
}
