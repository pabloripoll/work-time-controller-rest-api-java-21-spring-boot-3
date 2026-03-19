package api.dev.infrastructure.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    // Generates the SQL for this, just by the method name.
    Optional<UserJpaEntity> findByEmail(String email);
}
