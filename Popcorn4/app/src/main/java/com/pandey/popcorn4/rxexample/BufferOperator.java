package com.pandey.popcorn4.rxexample;

import android.os.Build;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import androidx.annotation.RequiresApi;
import io.reactivex.Observable;

import static java.util.concurrent.TimeUnit.SECONDS;

@RequiresApi(api = Build.VERSION_CODES.O)
public class BufferOperator {

    private static final LocalTime BUSINESS_START = LocalTime.of(9, 0);
    private static final LocalTime BUSINESS_END = LocalTime.of(17, 0);

    Observable<Duration> insideBusinessHours = Observable
            .interval(1, SECONDS)
            .filter(x -> isBusinessHour())
            .map(x -> Duration.ofMillis(100));
    Observable<Duration> outsideBusinessHours = Observable
            .interval(5, SECONDS)
            .filter(x -> !isBusinessHour())
            .map(x -> Duration.ofMillis(200));
    Observable<Duration> openings = Observable.merge(
            insideBusinessHours, outsideBusinessHours);


    private boolean isBusinessHour() {
        ZoneId zone = ZoneId.of("Europe/Warsaw");
        ZonedDateTime zdt = ZonedDateTime.now(zone);
        LocalTime localTime = zdt.toLocalTime();
        return !localTime.isBefore(BUSINESS_START)
                && !localTime.isAfter(BUSINESS_END);
    }

    public static void main(String[] args) {
        Observable<String> observable = Observable.fromArray("Hello", "Hi");

    }
}
