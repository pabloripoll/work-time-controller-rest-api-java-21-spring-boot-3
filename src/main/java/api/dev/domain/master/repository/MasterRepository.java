package api.dev.domain.master.repository;

import api.dev.domain.master.model.entity.Master;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface MasterRepository {
    Master save(Master master);
    Optional<Master> findById(Long id);
    Optional<Master> findByUserId(Long userId);
    Page<Master> findAll(int page, int perPage);
}
