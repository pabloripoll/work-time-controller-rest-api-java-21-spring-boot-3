package api.dev.infrastructure.persistence.repository.master;

import api.dev.infrastructure.persistence.jpa.master.MasterAccessLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterAccessLogJpaRepository extends JpaRepository<MasterAccessLogJpaEntity, Long> {
    List<MasterAccessLogJpaEntity> findByUserId(Long userId);
    Optional<MasterAccessLogJpaEntity> findByToken(String token);
}
