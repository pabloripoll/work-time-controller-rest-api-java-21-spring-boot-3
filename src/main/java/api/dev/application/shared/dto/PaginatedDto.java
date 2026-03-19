package api.dev.application.shared.dto;

import java.util.List;

public record PaginatedDto<T>(
        List<T> data,
        int page,
        int perPage,
        long total
) {}
