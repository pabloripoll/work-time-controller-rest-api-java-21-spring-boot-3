package api.dev.domain.master.repository;

import api.dev.domain.master.model.entity.MasterAccessLog;

import java.util.List;
import java.util.Optional;

public interface MasterAccessLogRepository {
    MasterAccessLog save(MasterAccessLog log);
    Optional<MasterAccessLog> findByToken(String token);
    List<MasterAccessLog> findAllByUserId(Long userId);
}
