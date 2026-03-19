package api.dev.infrastructure.persistence.repository.master;

import api.dev.infrastructure.persistence.jpa.master.MasterJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterJpaRepository extends JpaRepository<MasterJpaEntity, Long> {
    Optional<MasterJpaEntity> findByUserId(Long userId);
}
