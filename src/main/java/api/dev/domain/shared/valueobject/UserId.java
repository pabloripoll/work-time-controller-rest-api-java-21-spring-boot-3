package api.dev.domain.shared.valueobject;

import api.dev.domain.shared.exception.DomainException;

public record UserId(Long value) {

    public UserId {
        if (value == null || value <= 0) {
            throw new DomainException("UserId must be a positive number");
        }
    }

    // You can add helper methods if needed, or rely on toString()
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
