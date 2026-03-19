package api.dev.domain.shared.exception;

/**
 * Thrown when a requested Domain Entity does not exist in the persistence store.
 * This will later be caught by the GlobalExceptionHandler and translated to a 404 Not Found.
 */
public class EntityNotFoundException extends RuntimeException {

    // Standard constructor
    public EntityNotFoundException(String message) {
        super(message);
    }

    // Pro-tip: A helper constructor that automatically formats the message!
    // Example usage: throw new EntityNotFoundException(User.class, userId.value());
    public EntityNotFoundException(Class<?> entityClass, Object id) {
        super(String.format("%s with ID '%s' was not found.", entityClass.getSimpleName(), id));
    }
}
