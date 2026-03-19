package api.dev.infrastructure.persistence.jpa.adapter;

import api.dev.domain.shared.valueobject.Email;
import api.dev.domain.user.model.entity.User;
import api.dev.domain.user.repository.UserRepository;
import api.dev.infrastructure.persistence.jpa.mapper.UserJpaMapper;
import api.dev.infrastructure.persistence.repository.user.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
        var entity = mapper.toJpa(user);
        var saved  = jpaRepository.save(entity);
        // Sync the DB-generated ID back to the domain entity
        user.setId(saved.getId());
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        // UserJpaRepository works with String — extract the raw value from the record
        return jpaRepository.findByEmail(email.value()).map(mapper::toDomain);
    }
}
