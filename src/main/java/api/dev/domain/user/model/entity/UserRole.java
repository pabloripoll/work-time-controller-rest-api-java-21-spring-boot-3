package api.dev.domain.user.model.entity;

public enum UserRole {
    MASTER("ROLE_MASTER"),
    ADMIN("ROLE_ADMIN"),
    EMPLOYEE("ROLE_EMPLOYEE");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
