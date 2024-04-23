package cz.cvut.kbss.jopa.example10;

import cz.cvut.kbss.jopa.example10.model.Person;
import cz.cvut.kbss.jopa.example10.model.SimpleLiteralPerson;
import cz.cvut.kbss.jopa.example10.model.Term;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.MultilingualString;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.BasicWriterSettings;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

public class Example {

    private final Person person;
    private final SimpleLiteralPerson slPerson;

    private final Term term;

    private Example() {
        this.person = new Person();
        person.setUri(URI.create("http://example.org/jopa/example10/Thomas+Lasky"));
        person.setFirstName("Thomas");
        person.setLastName("Lasky");
        person.setEmail("lasky@unsc.org");
        this.slPerson = new SimpleLiteralPerson();
        slPerson.setUri(URI.create("http://example.org/jopa/example10/Sarah+Palmer"));
        slPerson.setFirstName("Sarah");
        slPerson.setLastName("Palmer");
        slPerson.setEmail("palmer@unsc.org");
        this.term = new Term();
        term.setUri(URI.create("http://xmlns.com/foaf/0.1/Person"));
        term.setLabel(MultilingualString.create("Person", "en"));
        term.getLabel().set("cs", "Osoba");
    }

    public static void main(String[] args) {
        final Example ex = new Example();
        ex.runWithPersistenceUnitLevelLanguage();
        ex.runWithoutPersistenceUnitLevelLanguage();
    }

    private void runWithPersistenceUnitLevelLanguage() {
        System.out.println("Running with persistence unit-level language set to: " + PersistenceFactory.LANGUAGE);
        final EntityManagerFactory emf = PersistenceFactory.createPersistenceUnitWithGlobalLanguage();
        try {
            run(emf, "withPersistenceUnitLanguage.ttl");
        } finally {
            emf.close();
        }
        System.out.println("--------------------------------------------------------\n\n");
    }

    private void run(EntityManagerFactory emf, String outputFile) {
        persistData(emf);
        outputData(emf, outputFile);
        readData(emf);
    }

    private void persistData(EntityManagerFactory emf) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        System.out.println("Persisting: " + person);
        em.persist(person);
        System.out.println("Persisting: " + slPerson);
        em.persist(slPerson);
        System.out.println("Persisting: " + term);
        em.persist(term);
        em.getTransaction().commit();
        em.close();
    }

    private void outputData(EntityManagerFactory emf, String outputFile) {
        final EntityManager em = emf.createEntityManager();
        final Repository repo = em.unwrap(Repository.class);
        try {
            final FileWriter out = new FileWriter(outputFile);
            try (final RepositoryConnection conn = repo.getConnection()) {
                RDFWriter writer = Rio.createWriter(RDFFormat.TURTLE, out);
                writer.getWriterConfig().set(BasicWriterSettings.PRETTY_PRINT, true);
                conn.export(writer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    private void readData(EntityManagerFactory emf) {
        final EntityManager em = emf.createEntityManager();
        System.out.println("Reading <" + slPerson.getUri() + "> as class " + Person.class.getSimpleName());
        final Person resultPerson = em.find(Person.class, slPerson.getUri());
        System.out.println("Read: " + resultPerson);
        System.out.println("Reading <" + person.getUri() + "> as class " + SimpleLiteralPerson.class.getSimpleName());
        final SimpleLiteralPerson resultSlPerson = em.find(SimpleLiteralPerson.class, person.getUri());
        System.out.println("Read: " + resultSlPerson);
        System.out.println("Reading <" + term.getUri() + "> as class " + Term.class.getSimpleName());
        final Term resultTerm = em.find(Term.class, term.getUri());
        System.out.println("Read: " + resultTerm);
        em.close();
    }

    private void runWithoutPersistenceUnitLevelLanguage() {
        System.out.println("Running without persistence unit-level language.");
        final EntityManagerFactory emf = PersistenceFactory.createPersistenceUnitWithoutGlobalLanguage();
        try {
            run(emf, "withoutPersistenceUnitLanguage.ttl");
        } finally {
            emf.close();
        }
        System.out.println("--------------------------------------------------------\n\n");
    }
}
