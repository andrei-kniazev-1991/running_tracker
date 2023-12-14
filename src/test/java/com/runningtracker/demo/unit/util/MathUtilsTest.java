package com.runningtracker.demo.unit.util;

import com.runningtracker.utils.MathUtils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MathUtilsTest {

    @Test
    void calculateDistanceBetweenPointsWithHypot_calculatedProperly() {

        double x1 = 0.0;
        double y1 = 0.0;
        double x2 = 3.0;
        double y2 = 4.0;

        double distance = MathUtils.calculateDistanceBetweenPointsWithHypot(x1, y1, x2, y2);

        assertEquals(5.0, distance, 0.0001);
    }

    @Test
    void calculateAverageSpeed_calculatedProperly() {
        LocalDateTime startTime = LocalDateTime.parse("2023-01-01T00:00:00");
        LocalDateTime finishTime = LocalDateTime.parse("2023-01-01T00:10:00");
        double distance = 300.0;

        double averageSpeed = MathUtils.calculateAverageSpeed(startTime, finishTime, distance);

        assertEquals(0.5, averageSpeed, 0.0001);
    }

    @Test
    void calculateDistanceBetweenPointsWithHypot_negativeValues_calculatedProperly() {

        double x1 = -1.0;
        double y1 = -1.0;
        double x2 = -4.0;
        double y2 = -5.0;

        double distance = MathUtils.calculateDistanceBetweenPointsWithHypot(x1, y1, x2, y2);

        assertEquals(5.0, distance, 0.0001);
    }

    @Test
    void calculateAverageSpeed_negativeDuration_calculatedProperly() {

        LocalDateTime startTime = LocalDateTime.parse("2023-01-01T00:10:00");
        LocalDateTime finishTime = LocalDateTime.parse("2023-01-01T00:00:00");
        double distance = 5.0;

        double averageSpeed = MathUtils.calculateAverageSpeed(startTime, finishTime, distance);

        assertEquals(0.0, averageSpeed, 0.0001);
    }

    @Test
    void calculateAverageSpeed_zeroDuration_calculatedProperly() {

        LocalDateTime startTime = LocalDateTime.parse("2023-01-01T00:00:00");
        LocalDateTime finishTime = LocalDateTime.parse("2023-01-01T00:00:00");
        double distance = 5.0;

        double averageSpeed = MathUtils.calculateAverageSpeed(startTime, finishTime, distance);

        assertEquals(0.0, averageSpeed, 0.0001);
    }
}
