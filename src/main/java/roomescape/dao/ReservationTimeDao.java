package roomescape.dao;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert timeInsert;

    public ReservationTimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime createTime(final LocalTime time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", time.format(DateTimeFormatter.ofPattern("HH:mm")));

        Number newId = timeInsert.executeAndReturnKey(parameters);
        return new ReservationTime(newId.longValue(), time);
    }

    public List<ReservationTime> findAllTimes() {
        return jdbcTemplate.query("select * from reservation_time", (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        ));
    }

    public Optional<ReservationTime> findTime(final Long id) {
        String sql = "select * from reservation_time where id = ?";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        ), id);
        return reservationTimes.stream().findFirst();
    }

    public void deleteTimeById(final Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }
}
