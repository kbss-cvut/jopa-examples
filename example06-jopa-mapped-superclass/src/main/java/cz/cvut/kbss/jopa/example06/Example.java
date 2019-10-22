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
package cz.cvut.kbss.jopa.example06;

import cz.cvut.kbss.jopa.example06.model.Person;
import cz.cvut.kbss.jopa.model.EntityManager;

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
