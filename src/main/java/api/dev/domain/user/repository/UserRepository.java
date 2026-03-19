package api.dev.domain.user.repository;

import api.dev.domain.shared.valueobject.Email;
import api.dev.domain.user.model.entity.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(Email email);
}