package io.github.application.lang;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)

public class LangCRUDTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @LocalServerPort
    private int port;
    @Before
    public void commonValues() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    public void getLangs() {

        jdbcTemplate.update("insert into languages (welcomeMsg, code) values ('Hola!', 'sp')");
        jdbcTemplate.update("insert into languages (welcomeMsg, code) values ('Ciao!', 'it')");
        Integer id1 = jdbcTemplate.queryForObject("select id from languages WHERE welcomeMsg = 'Hola!'", Integer.class);
        Integer id2 = jdbcTemplate.queryForObject("select id from languages WHERE welcomeMsg = 'Ciao!'", Integer.class);

        given()
        .when()
                .get("/api/langs")
        .then()
                .statusCode(200);

        SqlRowSet sqlRowSet1 = jdbcTemplate.queryForRowSet("select * from languages WHERE id=" + id1);
        assertThat(sqlRowSet1.next()).isTrue();

        SqlRowSet sqlRowSet2 = jdbcTemplate.queryForRowSet("select * from languages WHERE id=" + id2);
        assertThat(sqlRowSet2.next()).isTrue();
    }
}
