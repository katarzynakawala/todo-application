package io.github.application.hello;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)

public class HelloCRUDTests {

    @Autowired
    @LocalServerPort
    private int port;
    @Before
    public void commonValues() {
        RestAssured.baseURI = "http://localhost:" + port;
    }


    @Test
    public void getHello() {
    given()
    .when()
           .get("/api")
    .then()
           .statusCode(200)
           .body(containsString("Hello world"));
    }
}
