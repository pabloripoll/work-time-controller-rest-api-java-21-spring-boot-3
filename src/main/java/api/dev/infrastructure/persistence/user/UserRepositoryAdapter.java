package api.dev.infrastructure.persistence.user;

import api.dev.domain.shared.valueobject.Email;
import api.dev.domain.user.model.entity.User;
import api.dev.domain.user.repository.UserRepository;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Objects;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserJpaMapper mapper;

    public UserRepositoryAdapter(UserJpaRepository jpaRepository, UserJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper        = mapper;
    }

    @Override
    public User save(User user) {
        Objects.requireNonNull(user, "User must not be null");

        var entity = Objects.requireNonNull(mapper.toJpa(user), "UserJpaEntity must not be null");
        var saved  = jpaRepository.save(entity);

        // Sync the DB-generated ID back to the domain entity
        user.setId(saved.getId());

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) {
        Objects.requireNonNull(id, "User id must not be null");

        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        Objects.requireNonNull(email, "Email must not be null");

        return jpaRepository.findByEmail(email.value()).map(mapper::toDomain);
    }
}
