package api.dev.domain.shared.valueobject;

import api.dev.domain.shared.exception.DomainException;
import java.util.regex.Pattern;

// The "record" keyword automatically creates private final 'value',
// plus equals(), hashCode(), and toString()!
public record Email(String value) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,15}$");

    // This is a "Compact Constructor". It runs exactly when the record is instantiated.
    public Email {
        if (value == null || value.isBlank()) {
            throw new DomainException("Email cannot be empty");
        }

        value = value.trim().toLowerCase();

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new DomainException("Invalid email format: " + value);
        }
    }
}
