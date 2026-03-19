package api.dev.infrastructure.persistence.master;

import api.dev.domain.master.model.entity.Master;
import api.dev.domain.master.repository.MasterRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class MasterRepositoryAdapter implements MasterRepository {

    private final MasterJpaRepository jpaRepository;
    private final MasterJpaMapper mapper;

    public MasterRepositoryAdapter(MasterJpaRepository jpaRepository, MasterJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper        = mapper;
    }

    @Override
    public Master save(Master master) {
        var entity = Objects.requireNonNull(mapper.toJpa(master), "MasterJpaEntity must not be null");
        var saved  = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Master> findById(Long id) {
        Objects.requireNonNull(id, "Master id must not be null");
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Master> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId).map(mapper::toDomain);
    }

    @Override
    public Page<Master> findAll(int page, int perPage) {
        return jpaRepository.findAll(PageRequest.of(page, perPage)).map(mapper::toDomain);
    }
}
