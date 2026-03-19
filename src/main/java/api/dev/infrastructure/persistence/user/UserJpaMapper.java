package api.dev.infrastructure.persistence.user;

import api.dev.domain.shared.valueobject.Email;
import api.dev.domain.user.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserJpaMapper {

    public User toDomain(UserJpaEntity entity) {
        return new User(
                entity.getId(),
                new Email(entity.getEmail()),
                entity.getPassword(),
                entity.getRole(),
                entity.getCreatedByUserId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

    public UserJpaEntity toJpa(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId());
        entity.setEmail(user.getEmail().value());
        entity.setPassword(user.getPassword());
        entity.setRole(user.getRole());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());
        entity.setDeletedAt(user.getDeletedAt());
        entity.setCreatedByUserId(user.getCreatedByUserId());

        return entity;
    }
}
