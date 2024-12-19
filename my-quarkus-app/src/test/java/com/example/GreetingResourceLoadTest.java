package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class GreetingResourceLoadTest {

    @Test
    public void testDaysBetweenEndpointLoad() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate endDate = LocalDate.of(2026, 12, 31);
        List<Long> responseTimes = new ArrayList<>();
        AtomicInteger errorCount = new AtomicInteger(0);

        IntStream.rangeClosed(1, 365).forEach(dayOfYear -> {
            LocalDate startDate = LocalDate.ofYearDay(2023, dayOfYear);
            String startDateString = startDate.format(formatter);
            String endDateString = endDate.format(formatter);

            long startTime = System.currentTimeMillis();
            try {
                given()
                        .when().get("/hello/daysBetween?startDate=" + startDateString + "&endDate=" + endDateString)
                        .then()
                        .statusCode(200)
                        .body(containsString("daysBetween"));
            } catch (AssertionError e) {
                errorCount.incrementAndGet();
            }
            long endTime = System.currentTimeMillis();
            responseTimes.add(endTime - startTime);
        });

        double averageResponseTime = responseTimes.stream().mapToLong(Long::longValue).average().orElse(0);
        double errorRate = (double) errorCount.get() / 365 * 100;

        System.out.println("Average Response Time: " + averageResponseTime + " ms");
        System.out.println("Error Rate: " + errorRate + " %");
    }
}