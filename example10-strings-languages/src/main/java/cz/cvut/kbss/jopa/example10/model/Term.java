package cz.cvut.kbss.jopa.example10.model;

import cz.cvut.kbss.jopa.model.MultilingualString;
import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.OWLAnnotationProperty;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.vocabulary.SKOS;

import java.net.URI;

@OWLClass(iri = SKOS.CONCEPT)
public class Term {

    @Id
    private URI uri;

    @OWLAnnotationProperty(iri = SKOS.PREF_LABEL)
    private MultilingualString label;

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public MultilingualString getLabel() {
        return label;
    }

    public void setLabel(MultilingualString label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{<" + uri + "> " + label + '}';
    }
}
