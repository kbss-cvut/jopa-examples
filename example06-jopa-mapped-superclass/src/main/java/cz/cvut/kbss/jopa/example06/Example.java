package cz.cvut.kbss.jopa.example06;

import cz.cvut.kbss.jopa.example06.model.Person;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.PersistenceProperties;

public class Example {

    private final DataGenerator generator = new DataGenerator();

    private Example() {
        generator.generateData();
        PersistenceFactory.init();
    }

    public static void main(String[] args) {
        final Example example = new Example();
        example.persistDemoData();
        example.runDemo();

        PersistenceFactory.close();
    }

    private void persistDemoData() {
        System.out.println("Persisting demo data...");
        final EntityManager em = PersistenceFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            generator.getAuthors().forEach(em::persist);
            generator.getOccurrenceReports().forEach(em::persist);
            generator.getAuditReports().forEach(em::persist);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private void runDemo() {
        final EntityManager em = PersistenceFactory.createEntityManager();
        final ReportDao dao = new ReportDao(em);
        final DataOutput output = new DataOutput();
        output.printReports("All Occurrence reports", dao.findOccurrenceReports());
        System.out.println("\n\n");
        output.printReports("All Audit reports", dao.findAuditReports());
        for (Person author : generator.getAuthors()) {
            System.out.println("\n\n");
            output.printReports("Occurrence reports by " + author, dao.findOccurrenceReports(author));
            System.out.println("\n\n");
            output.printReports("Audit reports by " + author, dao.findAuditReports(author));
        }
    }
}
