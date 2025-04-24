package roomescape.api;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.core.Is.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.config.TestClockConfig;
import roomescape.domain.ReservationTime;
import roomescape.fixture.ReservationTimeFixture;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(TestClockConfig.class)
class ReservationTimeApiTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final ReservationTimeFixture reservationTimeFixture = new ReservationTimeFixture();

    @Test
    void 예약_시간을_생성한다() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "startAt", "10:00"
                ))
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));
        SoftAssertions softly = new SoftAssertions();
        List<ReservationTime> allReservationTime = findAllReservationTime();
        softly.assertThat(allReservationTime).hasSize(1);
        ReservationTime reservationTime = allReservationTime.getFirst();
        softly.assertThat(reservationTime.id()).isEqualTo(1L);
        softly.assertThat(reservationTime.time()).isEqualTo(LocalTime.of(10, 0, 0));
    }

    @Test
    void 예약_시간을_조회한다() {
        ReservationTime reservationTime = reservationTimeFixture.예약시간_10시();
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
        SoftAssertions softly = new SoftAssertions();
        List<ReservationTime> allReservationTime = findAllReservationTime();
        softly.assertThat(allReservationTime).hasSize(1);
        ReservationTime savedReservation = allReservationTime.getFirst();
        softly.assertThat(savedReservation.id()).isEqualTo(reservationTime.id());
        softly.assertThat(savedReservation.time()).isEqualTo(reservationTime.time());
    }

    @Test
    void 예약_시간을_삭제한다() {
        reservationTimeFixture.예약시간_10시();
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
        assertThat(findAllReservationTime()).hasSize(0);
    }


    public List<ReservationTime> findAllReservationTime() {
        return jdbcTemplate.query("select * from reservation_time", (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))
        ));
    }
}
