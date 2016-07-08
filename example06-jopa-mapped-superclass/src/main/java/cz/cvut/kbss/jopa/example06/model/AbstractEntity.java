package cz.cvut.kbss.jopa.example06.model;

import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.MappedSuperclass;

import java.io.Serializable;
import java.net.URI;

/**
 * Base class for all entities.
 * <p>
 * Declares just the identifier and getter/setter for it.
 */
@MappedSuperclass
abstract class AbstractEntity implements Serializable {

    @Id(generated = true)
    private URI uri;

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
