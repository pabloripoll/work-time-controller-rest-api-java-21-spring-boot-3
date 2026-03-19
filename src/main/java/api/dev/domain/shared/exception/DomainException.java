package api.dev.domain.shared.exception;

/**
 * Base exception for all Domain-level validation and business rule violations.
 * We extend RuntimeException so we don't have to declare "throws DomainException"
 * on every single method signature in our interfaces.
 */
public class DomainException extends RuntimeException {

    // This fixes: "The constructor DomainException(String) is undefined"
    public DomainException(String message) {
        super(message);
    }

    // It is also good practice to include a constructor that accepts a cause (another exception)
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
