package api.dev.domain.shared.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Thrown when input data fails domain or application validation rules.
 * This will later be caught by the GlobalExceptionHandler and translated to a 400 Bad Request or 422 Unprocessable Entity.
 */
public class ValidationException extends DomainException {

    private final Map<String, String> errors;

    // Constructor for a single general validation message
    public ValidationException(String message) {
        super(message);
        this.errors = new HashMap<>();
    }

    // Constructor for multiple field-specific validation errors (e.g., {"email": "invalid format", "password": "too short"})
    public ValidationException(Map<String, String> errors) {
        super("Validation failed for one or more fields.");
        this.errors = errors;
    }

    // Constructor that takes both a message and a map of errors
    public ValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
