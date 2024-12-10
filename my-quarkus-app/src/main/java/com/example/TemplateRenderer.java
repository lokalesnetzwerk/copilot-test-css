package com.example;

public class TemplateRenderer {

    public static String renderInvalidDateFormat() {
        return "<div style='text-align: center; font-size: 24px; color: red;'>Invalid date format. Please use dd-MM-yyyy.</div>";
    }

    public static String renderDaysBetween(long daysBetween) {
        return "<div style='text-align: center; font-size: 64px; color: rainbow;'>" + daysBetween + " days</div>";
    }
}