package tech.lemnova.continuum_backend.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s not found with id: %s", resource, id));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
