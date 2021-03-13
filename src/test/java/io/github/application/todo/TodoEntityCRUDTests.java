package io.github.application.todo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TodoEntityCRUDTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @LocalServerPort
    private int port;

    @Before
    public void commonValues() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @After
    public void after() {
        jdbcTemplate.update("delete from todos");
    }

    @Test
    public void postTodoHappyPath() {

        //creating body
        Map<String,Object> jsonBody = new HashMap<>();
        jsonBody.put("text", "posting123");
        jsonBody.put("done", false);

        given()
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-Type", "application/json")
                .body(jsonBody)
        .when()
                .post("/api/todos")
        .then()
                .statusCode(200);

        Integer id = jdbcTemplate.queryForObject("select id from todos WHERE text = 'posting123'", Integer.class);
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from todos WHERE id=" + id);
        assertThat(sqlRowSet.next()).isTrue();
    }

    @Test
    public void postTodoNullValues() {

        //creating body
        Map<String,Object> jsonBody = new HashMap<>();
        jsonBody.put("text", null);
        jsonBody.put("done", null);

        given()
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-Type", "application/json")
                .body(jsonBody)
        .when()
                .post("/api/todos")
        .then()
                .statusCode(400);
    }

    @Test
    public void getTodo() {

        jdbcTemplate.update("insert into todos (text, done) values ('firstToGet', 0)");
        jdbcTemplate.update("insert into todos (text, done) values ('secondToGet', 1)");
        Integer id1 = jdbcTemplate.queryForObject("select id from todos WHERE text = 'firstToGet'", Integer.class);
        Integer id2 = jdbcTemplate.queryForObject("select id from todos WHERE text = 'firstToGet'", Integer.class);

        given()
        .when()
                .get("/api/todos")
        .then()
                .statusCode(200);

        SqlRowSet sqlRowSet1 = jdbcTemplate.queryForRowSet("select * from todos WHERE id=" + id1);
        assertThat(sqlRowSet1.next()).isTrue();

        SqlRowSet sqlRowSet2 = jdbcTemplate.queryForRowSet("select * from todos WHERE id=" + id2);
        assertThat(sqlRowSet2.next()).isTrue();
    }


    @Test
    public void putTodoHappyPath() {

        jdbcTemplate.update("insert into todos (text, done) values ('putting', 0)");
        Integer id = jdbcTemplate.queryForObject("select id from todos WHERE text = 'putting'", Integer.class);

        Map<String,Object> updateBody = new HashMap<>();
        updateBody.put("text", "test");
        updateBody.put("done", true);

        given()
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-Type", "application/json")
                .body(updateBody)
        .when()
                .put("/api/todos/" + id)
        .then()
                .log().all()
                .statusCode(200);
                //.body("done", equalTo(true));

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from todos WHERE id=" + id);
        assertThat(sqlRowSet.next()).isTrue();
    }


    @Test
    public void getTodoById() {

        jdbcTemplate.update("insert into todos (text, done) values ('getting', 1)");
        Integer id = jdbcTemplate.queryForObject("select id from todos WHERE text = 'getting'", Integer.class);

        given()
        .when()
                .get("/api/todos/" + id)
        .then()
                .statusCode(200);

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from todos WHERE id=" + id);
        assertThat(sqlRowSet.next()).isTrue();
    }

    @Test
    public void deleteTodo() {

       jdbcTemplate.update("insert into todos (text, done) values ('test123', 1)");
        Integer id = jdbcTemplate.queryForObject("select id from todos WHERE text = 'test123'", Integer.class);

        //delete
        given()
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .header("Content-Type", "application/json")
        .when()
                .delete("/api/todos/" + id)
        .then()
                .log().all()
                .statusCode(204);

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from todos WHERE id=" + id);
        assertThat(sqlRowSet.next()).isFalse();
    }
}
