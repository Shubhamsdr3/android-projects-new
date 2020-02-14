package com.pandey.popcorn4;

import androidx.annotation.NonNull;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class GlobalBuses {


    private PublishSubject<String> changeBus = PublishSubject.create();

    public void send(@NonNull String message) {
        changeBus.onNext(message);
    }


    @Singleton
    public Observable<String> toObservable() {
        return changeBus;
    }
}
