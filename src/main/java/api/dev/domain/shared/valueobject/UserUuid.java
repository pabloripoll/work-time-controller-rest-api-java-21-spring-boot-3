package api.dev.domain.shared.valueobject;

import api.dev.domain.shared.exception.DomainException;
import java.util.UUID;

public record UserUuid(UUID value) {

    public UserUuid {
        if (value == null) {
            throw new DomainException("UserId cannot be null");
        }
    }

    // Helper to generate a new random ID
    public static UserUuid generate() {
        return new UserUuid(UUID.randomUUID());
    }

    // Helper to recreate from a database string
    public static UserUuid fromString(String uuid) {
        return new UserUuid(UUID.fromString(uuid));
    }
}
