package com.runningtracker.utils;

import java.time.Duration;
import java.time.LocalDateTime;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtils {

    public double calculateDistanceBetweenPointsWithHypot(double x1, double y1, double x2, double y2) {

        double ac = Math.abs(y2 - y1);
        double cb = Math.abs(x2 - x1);

        return Math.hypot(ac, cb);
    }

    public double calculateAverageSpeed(LocalDateTime startTime, LocalDateTime finishTime, double distance) {

        Duration duration = Duration.between(startTime, finishTime);
        double seconds = duration.getSeconds();
        return seconds > 0 ? distance / seconds : 0;
    }
}
