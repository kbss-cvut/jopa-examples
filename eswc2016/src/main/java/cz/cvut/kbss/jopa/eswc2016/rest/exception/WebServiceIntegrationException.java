package cz.cvut.kbss.jopa.eswc2016.rest.exception;

public class WebServiceIntegrationException extends RuntimeException {

    public WebServiceIntegrationException() {
    }

    public WebServiceIntegrationException(String message) {
        super(message);
    }

    public WebServiceIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
