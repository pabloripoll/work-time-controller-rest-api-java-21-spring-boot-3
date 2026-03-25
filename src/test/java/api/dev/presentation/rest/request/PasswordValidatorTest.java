package api.dev.presentation.rest.request;

import api.dev.domain.shared.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Unit] PasswordValidator")
class PasswordValidatorTest {

    // ------------------------------------------------------------------ //
    // Valid passwords
    // ------------------------------------------------------------------ //

    @Test
    @DisplayName("passes when password meets all requirements")
    void validate_validPassword_noException() {
        assertDoesNotThrow(() -> PasswordValidator.validate("Pass12B4?"));
    }

    // ------------------------------------------------------------------ //
    // Null / blank
    // ------------------------------------------------------------------ //

    @Test
    @DisplayName("throws when password is null")
    void validate_null_throws() {
        var ex = assertThrows(ValidationException.class,
                () -> PasswordValidator.validate(null));
        assertEquals("Password is required", ex.getMessage());
    }

    @Test
    @DisplayName("throws when password is blank")
    void validate_blank_throws() {
        var ex = assertThrows(ValidationException.class,
                () -> PasswordValidator.validate("   "));
        assertEquals("Password is required", ex.getMessage());
    }

    // ------------------------------------------------------------------ //
    // Too short
    // ------------------------------------------------------------------ //

    @Test
    @DisplayName("throws when password is shorter than 8 characters")
    void validate_tooShort_throws() {
        var ex = assertThrows(ValidationException.class,
                () -> PasswordValidator.validate("Ab1?"));
        assertEquals("Password must be at least 8 characters", ex.getMessage());
    }

    // ------------------------------------------------------------------ //
    // Missing character classes
    // ------------------------------------------------------------------ //

    @ParameterizedTest(name = "[{index}] ''{0}'' missing a required character class")
    @ValueSource(strings = {
            "password1?",    // no uppercase
            "PASSWORD1?",    // no lowercase
            "Password??",    // no number
            "Password12",    // no special char
    })
    @DisplayName("throws when password is missing a required character class")
    void validate_missingCharClass_throws(String password) {
        var ex = assertThrows(ValidationException.class,
                () -> PasswordValidator.validate(password));
        assertEquals(
                "Password must contain at least one uppercase letter, one lowercase letter, one number and one special character",
                ex.getMessage()
        );
    }
}
