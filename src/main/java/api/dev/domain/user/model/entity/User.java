package api.dev.domain.user.model.entity;

import api.dev.domain.shared.valueobject.Email;
import java.time.LocalDateTime;

public class User {

    private Long id;
    private Email email;
    private String password;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    // Private constructor enforces the use of static factory methods
    private User(Long id, Email email, String password, UserRole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }

    // Constructor for recreating the entity from the Database (via Repository/Mapper)
    public User(
            Long id,
            Email email,
            String password,
            UserRole role,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // --- STATIC FACTORIES (BUSINESS METHODS) ---

    public static User createMaster(Long id, Email email, String password) {
        return new User(id, email, password, UserRole.MASTER);
    }

    public static User createAdmin(Long id, Email email, String password) {
        return new User(id, email, password, UserRole.ADMIN);
    }

    public static User createEmployee(Long id, Email email, String password) {
        return new User(id, email, password, UserRole.EMPLOYEE);
    }

    // --- BUSINESS ACTIONS ---

    public void updatePassword(String hashedPassword) {
        this.password = hashedPassword;
        this.markAsUpdated();
    }

    public void updateEmail(Email email) {
        this.email = email;
        this.markAsUpdated();
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
        this.markAsUpdated();
    }

    public void restore() {
        this.deletedAt = null;
        this.markAsUpdated();
    }

    private void markAsUpdated() {
        this.updatedAt = LocalDateTime.now();
    }

    // --- CHECKS ---

    public boolean isMaster() {
        return this.role == UserRole.MASTER;
    }

    public boolean isAdmin() {
        return this.role == UserRole.ADMIN;
    }

    public boolean isEmployee() {
        return this.role == UserRole.EMPLOYEE;
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    // --- GETTERS & SETTERS ---

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; } // Used by JPA Mapper after persistence

    public Email getEmail() { return email; }

    public String getPassword() { return password; }

    public UserRole getRole() { return role; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
}
