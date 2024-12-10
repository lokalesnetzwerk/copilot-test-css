package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello from Quarkus REST"));
    }

    @Test
    public void testDaysBetweenEndpoint() {
        given()
          .when().get("/hello/daysBetween?startDate=2023-01-01&endDate=2023-01-10")
          .then()
             .statusCode(200)
             .body(is("<div style='text-align: center; font-size: 64px; color: rainbow;'>9 days</div>"));
    }
}