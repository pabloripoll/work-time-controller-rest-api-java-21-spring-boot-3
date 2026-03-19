package api.dev.domain.master.model.valueobject;

import java.util.Objects;

public final class MasterId {

    private final Long value;

    private MasterId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("MasterId must be a positive number");
        }
        this.value = value;
    }

    public static MasterId of(Long value) {
        return new MasterId(value);
    }

    public Long getValue() { return value; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MasterId)) return false;
        return Objects.equals(value, ((MasterId) o).value);
    }

    @Override public int hashCode() { return Objects.hash(value); }
    @Override public String toString() { return value.toString(); }
}
