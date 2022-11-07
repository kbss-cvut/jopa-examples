package cz.cvut.kbss.jopa.example04.environment;

import org.eclipse.rdf4j.repository.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@Configuration
public class TestRdf4jPersistenceProvider {

    @Bean
    @Primary
    public Repository repository() {
        return mock(Repository.class);
    }
}
