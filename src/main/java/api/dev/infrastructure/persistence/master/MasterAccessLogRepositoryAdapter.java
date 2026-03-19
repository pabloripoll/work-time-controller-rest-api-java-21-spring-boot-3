package api.dev.infrastructure.persistence.master;

import api.dev.domain.master.model.entity.MasterAccessLog;
import api.dev.domain.master.repository.MasterAccessLogRepository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class MasterAccessLogRepositoryAdapter implements MasterAccessLogRepository {

    private final MasterAccessLogJpaRepository jpaRepository;
    private final MasterJpaMapper mapper;

    public MasterAccessLogRepositoryAdapter(MasterAccessLogJpaRepository jpaRepository,
                                             MasterJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper        = mapper;
    }

    @Override
    public MasterAccessLog save(MasterAccessLog log) {
        Objects.requireNonNull(log, "MasterAccessLog must not be null");

        var entity = Objects.requireNonNull(mapper.toJpa(log), "MasterAccessLogJpaEntity must not be null");
        var saved  = jpaRepository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<MasterAccessLog> findByToken(String token) {
        return jpaRepository.findByToken(token).map(mapper::toDomain);
    }

    @Override
    public List<MasterAccessLog> findAllByUserId(Long userId) {
        return jpaRepository.findByUserId(userId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
