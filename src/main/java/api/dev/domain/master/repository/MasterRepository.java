package api.dev.domain.master.repository;

import api.dev.domain.master.model.entity.Master;

import java.util.Optional;

public interface MasterRepository {
    Master save(Master master);
    Optional<Master> findById(Long id);
    Optional<Master> findByUserId(Long userId);
}
