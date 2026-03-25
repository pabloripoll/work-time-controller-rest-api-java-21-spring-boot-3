package api.dev.presentation.rest.request;

import api.dev.domain.shared.exception.ValidationException;

public class PasswordValidator {

    private static final int    MIN_LENGTH = 8;
    private static final String REGEX      = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$";

    public static void validate(String password) {
        if (password == null || password.isBlank()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < MIN_LENGTH) {
            throw new ValidationException("Password must be at least 8 characters");
        }
        if (!password.matches(REGEX)) {
            throw new ValidationException("Password must contain at least one uppercase letter, one lowercase letter, one number and one special character");
        }
    }
}
