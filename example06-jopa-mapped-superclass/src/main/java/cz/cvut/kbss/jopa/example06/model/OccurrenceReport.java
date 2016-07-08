package cz.cvut.kbss.jopa.example06.model;

import cz.cvut.kbss.jopa.CommonVocabulary;
import cz.cvut.kbss.jopa.model.annotations.*;

import java.net.URI;
import java.util.Date;

@OWLClass(iri = Vocabulary.OccurrenceReport)
public class OccurrenceReport extends Report {

    @OWLDataProperty(iri = Vocabulary.startTime)
    private Date startTime;

    @OWLDataProperty(iri = Vocabulary.endTime)
    private Date endTime;

    @OWLObjectProperty(iri = Vocabulary.hasSeverity)
    private URI severity;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public URI getSeverity() {
        return severity;
    }

    public void setSeverity(URI severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "OccurrenceReport{" +
                "severity=" + severity +
                "} " + super.toString();
    }

    @Override
    public void printTableHeader() {
        System.out.println(String.format("%1$-177s", "").replace(' ', '-'));
        System.out.println(
                "|" + String.format("%1$-20s", "Headline") + "|" + String.format("%1$-30s", "Start time") + "|" +
                        String.format("%1$-30s", "End time") + "|" + String.format("%1$-75s", "Severity") + "|" +
                        String.format("%1$16s", "Author") + "|");
        System.out.println(String.format("%1$-177s", "").replace(' ', '-'));
    }

    @Override
    public void printTableRow() {
        System.out.println("|" + String.format("%1$-20s",
                getHeadline()) + "|" + String.format("%1$-30s", startTime) + "|" + String.format("%1$-30s", endTime) +
                        "|" + String.format("%1$-75s", severity) + "|" + String.format("%1$16s", getAuthor()) + "|");
    }
}
