package api.dev.domain.user.model.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    MASTER("ROLE_MASTER"),
    ADMIN("ROLE_ADMIN"),
    EMPLOYEE("ROLE_EMPLOYEE");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    @JsonValue
    public String getRoleName() {
        return roleName;
    }
}
