package com.pandey.popcorn4.rxexample;

import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TimeoutHandling {

    Observable<Confirmation> confirmation() {
        Observable<Confirmation> delayBeforeCompletion =
                Observable
                        .<Confirmation>empty()
                        .delay(200, MILLISECONDS);
        return Observable
                .just(new Confirmation())
                .delay(100, MILLISECONDS)
                .concatWith(delayBeforeCompletion);
    }

    public static void main(String args[]) {
        TimeoutHandling timeoutHandling = new TimeoutHandling();
        timeoutHandling.confirmation();
    }


}


class Confirmation {

}
