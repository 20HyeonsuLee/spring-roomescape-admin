package roomescape.fixture;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.config.JdbcTemplateInitializer;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationName;
import roomescape.domain.ReservationTime;

public class ReservationFixture {
    private final JdbcTemplate jdbcTemplate = JdbcTemplateInitializer.getJdbcTemplate();

    public Reservation 예약_한스_25_4_22(ReservationTime reservationTime) {
        ReservationName reservationName = ReservationNameFixture.한스;
        LocalDate reservationDate = LocalDate.of(2025, 4, 22);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation(name, date, time_id) values(?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, reservationName.getValue());
            statement.setString(2, reservationDate.toString());
            statement.setLong(3, reservationTime.id());
            return statement;
        }, keyHolder);
        return new Reservation(
                keyHolder.getKey().longValue(),
                reservationName,
                new ReservationDate(reservationDate),
                reservationTime
        );
    }
}
