package api.dev.infrastructure.persistence.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterJpaRepository extends JpaRepository<MasterJpaEntity, Long> {
    Optional<MasterJpaEntity> findByUserId(Long userId);
}
