package cz.cvut.kbss.jopa.example06;

import cz.cvut.kbss.jopa.example06.model.Report;

import java.util.Collection;

class DataOutput {

    void printReports(String tableHeadline, Collection<? extends Report> reports) {
        System.out.println(String.format("%1$-53s", "").replace(' ', '-'));
        System.out.println("|" + String.format("%1$-51s", tableHeadline) + "|");
        if (!reports.isEmpty()) {
            reports.iterator().next().printTableHeader();
            reports.forEach(Report::printTableRow);
        }
    }
}
