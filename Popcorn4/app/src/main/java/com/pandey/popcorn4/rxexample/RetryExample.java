package com.pandey.popcorn4.rxexample;

import io.reactivex.Observable;
import timber.log.Timber;

import static java.util.concurrent.TimeUnit.SECONDS;

public class RetryExample {

    static Observable<String> risky() {
        return Observable.fromCallable(() -> {
            if (Math.random() < 0.1) {
                Thread.sleep((long) (Math.random() * 2000));
                return "OK";
            } else {
                throw new RuntimeException("Transient");
            }
        });
    }


    public static void main(String args[]) {
        RetryExample.risky()
                .timeout(1, SECONDS)
                .doOnError(throwable -> Timber.i("Retrying...%s",throwable))
                .retry()
                .subscribe(System.out::println);
    }

}
