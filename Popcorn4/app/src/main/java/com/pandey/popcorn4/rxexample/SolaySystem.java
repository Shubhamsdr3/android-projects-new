package com.pandey.popcorn4.rxexample;

import android.os.Build;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import androidx.annotation.RequiresApi;
import io.reactivex.Observable;

import static java.util.Calendar.AUGUST;
import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.FEBRUARY;
import static java.util.Calendar.JANUARY;
import static java.util.Calendar.JULY;
import static java.util.Calendar.MARCH;
import static java.util.Calendar.SEPTEMBER;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class SolaySystem {

    @RequiresApi(api = Build.VERSION_CODES.O)
    Observable<LocalDate> nextSolarEclipse(LocalDate after) {
        return Observable
                .just(
                        LocalDate.of(2016, MARCH, 9),
                        LocalDate.of(2016, SEPTEMBER, 1),
                        LocalDate.of(2017, FEBRUARY, 26),
                        LocalDate.of(2017, AUGUST, 21),
                        LocalDate.of(2018, FEBRUARY, 15),
                        LocalDate.of(2018, JULY, 13),
                        LocalDate.of(2018, AUGUST, 11),
                        LocalDate.of(2019, JANUARY, 6),
                        LocalDate.of(2019, JULY, 2),
                        LocalDate.of(2019, DECEMBER, 26))
                .skipWhile(date -> !date.isAfter(after))
                .zipWith(
                        Observable.interval(500, 50, MILLISECONDS),
                        (date, x) -> date);
    }

    public static void main(String[] argds) {
        SolaySystem solaySystem = new SolaySystem();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            solaySystem.nextSolarEclipse(LocalDate.of(2016, SEPTEMBER, 1))
                    .timeout(Observable.timer(1000, TimeUnit.MILLISECONDS),
                            date -> Observable.timer(100, MILLISECONDS));
        }
    }
}
