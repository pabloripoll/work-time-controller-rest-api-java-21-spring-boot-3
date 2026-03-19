package api.dev.domain.master.model.valueobject;

import java.util.Objects;

public final class MasterNickname {

    private static final int MAX_LENGTH = 64;
    private final String value;

    private MasterNickname(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Nickname cannot be blank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Nickname cannot exceed " + MAX_LENGTH + " characters");
        }
        this.value = value.trim();
    }

    public static MasterNickname of(String value) {
        return new MasterNickname(value);
    }

    public String getValue() { return value; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MasterNickname)) return false;
        return Objects.equals(value, ((MasterNickname) o).value);
    }

    @Override public int hashCode() { return Objects.hash(value); }
    @Override public String toString() { return value; }
}
