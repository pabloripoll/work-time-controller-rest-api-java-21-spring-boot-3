package api.dev.infrastructure.persistence.jpa.mapper;

import api.dev.domain.master.model.entity.Master;
import api.dev.domain.master.model.entity.MasterAccessLog;
import api.dev.domain.master.model.entity.MasterProfile;
import api.dev.domain.user.model.entity.User;
import api.dev.infrastructure.persistence.jpa.master.MasterAccessLogJpaEntity;
import api.dev.infrastructure.persistence.jpa.master.MasterJpaEntity;
import api.dev.infrastructure.persistence.jpa.master.MasterProfileJpaEntity;
import api.dev.infrastructure.persistence.repository.user.UserJpaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Component
public class MasterJpaMapper {

    private final UserJpaRepository userJpaRepository;
    private final UserJpaMapper userJpaMapper;
    private final ObjectMapper objectMapper;

    public MasterJpaMapper(UserJpaRepository userJpaRepository,
                           UserJpaMapper userJpaMapper,
                           ObjectMapper objectMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userJpaMapper     = userJpaMapper;
        this.objectMapper      = objectMapper;
    }

    // ------------------------------------------------------------------ //
    // Master
    // ------------------------------------------------------------------ //

    public Master toDomain(MasterJpaEntity entity) {
        Long userId = Objects.requireNonNull(entity.getUserId(), "Master userId must not be null");
        var userJpaEntity = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User not found for master userId: " + userId));

        User user = userJpaMapper.toDomain(userJpaEntity);

        MasterProfile profile = entity.getProfile() != null
                ? toDomain(entity.getProfile())
                : null;

        return Master.reconstitute(
                entity.getId(),
                user,
                entity.isActive(),
                entity.isBanned(),
                profile,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public MasterJpaEntity toJpa(Master master) {
        MasterJpaEntity entity = new MasterJpaEntity();
        entity.setId(master.getId());
        entity.setUserId(master.getUser().getId());
        entity.setActive(master.isActive());
        entity.setBanned(master.isBanned());
        entity.setCreatedAt(master.getCreatedAt() != null ? master.getCreatedAt() : LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        if (master.getProfile() != null) {
            MasterProfileJpaEntity profileEntity = toJpa(master.getProfile());
            profileEntity.setMaster(entity);
            entity.setProfile(profileEntity);
        }

        return entity;
    }

    // ------------------------------------------------------------------ //
    // MasterProfile
    // ------------------------------------------------------------------ //

    public MasterProfile toDomain(MasterProfileJpaEntity entity) {
        return MasterProfile.reconstitute(
                entity.getId(),
                entity.getNickname(),
                entity.getAvatar(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public MasterProfileJpaEntity toJpa(MasterProfile profile) {
        MasterProfileJpaEntity entity = new MasterProfileJpaEntity();
        entity.setId(profile.getId());
        entity.setNickname(profile.getNickname());
        entity.setAvatar(profile.getAvatar());
        entity.setCreatedAt(profile.getCreatedAt() != null ? profile.getCreatedAt() : LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }

    // ------------------------------------------------------------------ //
    // MasterAccessLog
    // ------------------------------------------------------------------ //

    public MasterAccessLog toDomain(MasterAccessLogJpaEntity entity) {
        Long userId = Objects.requireNonNull(entity.getUserId(), "AccessLog userId must not be null");
        var userJpaEntity = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User not found for access log userId: " + userId));

        User user = userJpaMapper.toDomain(userJpaEntity);

        Map<String, Object> payload = jsonToMap(entity.getPayload());

        return MasterAccessLog.reconstitute(
                entity.getId(),
                user,
                entity.getToken(),
                entity.getExpiresAt(),
                entity.getIpAddress(),
                entity.getUserAgent(),
                payload,
                entity.isTerminated(),
                entity.isExpired(),
                entity.getRefreshCount(),
                entity.getRequestsCount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public MasterAccessLogJpaEntity toJpa(MasterAccessLog log) {
        MasterAccessLogJpaEntity entity = new MasterAccessLogJpaEntity();
        entity.setId(log.getId());
        entity.setUserId(log.getUser().getId());
        entity.setTerminated(log.isTerminated());
        entity.setExpired(log.isExpired());
        entity.setExpiresAt(log.getExpiresAt());
        entity.setRefreshCount(log.getRefreshCount());
        entity.setIpAddress(log.getIpAddress());
        entity.setUserAgent(log.getUserAgent());
        entity.setRequestsCount(log.getRequestsCount());
        entity.setPayload(mapToJson(log.getPayload()));
        entity.setToken(log.getToken());
        entity.setCreatedAt(log.getCreatedAt() != null ? log.getCreatedAt() : LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }

    // ------------------------------------------------------------------ //
    // JSON helpers
    // ------------------------------------------------------------------ //

    private Map<String, Object> jsonToMap(String json) {
        if (json == null || json.isBlank()) return null;
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to deserialize payload JSON: " + json, e);
        }
    }

    private String mapToJson(Map<String, Object> map) {
        if (map == null) return null;
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialize payload map", e);
        }
    }
}
