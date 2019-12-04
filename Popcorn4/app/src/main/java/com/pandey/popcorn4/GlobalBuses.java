package com.pandey.popcorn4;

import androidx.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class GlobalBuses {


    private PublishSubject<String> changeBus = PublishSubject.create();

    public void send(@NonNull String message) {
        changeBus.onNext(message);
    }


    public Observable<String> toObservable() {
        return changeBus;
    }
}
