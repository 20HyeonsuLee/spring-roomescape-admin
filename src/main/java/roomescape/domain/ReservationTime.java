package roomescape.domain;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {
    private final Long id;
    private final LocalTime time;

    public ReservationTime(final Long id, final LocalTime time) {
        this.id = Objects.requireNonNull(id, "예약 id는 null일 수 없습니다.");
        this.time = Objects.requireNonNull(time, "예약 시간은 null일 수 없습니다.");
    }

    public Long id() {
        return id;
    }

    public LocalTime time() {
        return time;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationTime that)) {
            return false;
        }
        return Objects.equals(id, that.id) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time);
    }
}
