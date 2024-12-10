package com.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @GET
    @Path("/daysBetween")
    @Produces(MediaType.TEXT_HTML)
    public Response daysBetween(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        if (!isValidDate(startDate, formatter) || !isValidDate(endDate, formatter)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("<div style='text-align: center; font-size: 24px; color: red;'>Invalid date format. Please use dd-MM-yyyy.</div>")
                    .build();
        }

        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        long daysBetween = ChronoUnit.DAYS.between(start, end);

        return Response.ok("<div style='text-align: center; font-size: 64px; color: rainbow;'>"
                + daysBetween + " days</div>").build();
    }

    private boolean isValidDate(String date, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}