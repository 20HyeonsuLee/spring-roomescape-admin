package roomescape.fixture;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.config.JdbcTemplateInitializer;
import roomescape.domain.ReservationTime;

public class ReservationTimeFixture {
    private final JdbcTemplate jdbcTemplate = JdbcTemplateInitializer.getJdbcTemplate();

    public ReservationTime 예약시간_10시() {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation_time(start_at) values(?)";
        LocalTime startAt = LocalTime.of(10, 0);
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, startAt.toString());
            return statement;
        }, keyHolder);
        return new ReservationTime(keyHolder.getKey().longValue(), startAt);
    }
}
