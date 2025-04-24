package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private final Long id;
    private final ReservationName name;
    private final ReservationDate date;
    private final ReservationTime time;

    public Reservation(
            final Long id,
            final ReservationName name,
            final ReservationDate date,
            final ReservationTime time
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name.getValue();
    }

    public LocalDate date() {
        return date.date();
    }

    public LocalTime time() {
        return time.time();
    }

    public ReservationTime reservationTime() {
        return time;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation that)) {
            return false;
        }
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, time);
    }
}
