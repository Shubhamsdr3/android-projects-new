package com.pandey.popcorn4.rxexample;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DishWash {

    Observable<Integer> myRange(int from, int count) {
        return Observable.create(subscriber -> {
            int i = from;
            while (i < from + count) {
                if (!subscriber.isDisposed()) {
                    subscriber.onNext(i++);
                } else {
                    return;
                }
            }
            subscriber.onComplete();
        });
    }
    @SuppressLint("CheckResult")
    public static void main(String args[]) {
        DishWash dishWash = new DishWash();
        dishWash.myRange(1, 1_000_000_000)
                .map(Dish::new)
                .observeOn(Schedulers.io())
                .subscribe(__ -> {
                            System.out.println("Washing: " + __);
                            Thread.sleep(50);
                        },
                        Throwable::printStackTrace
                );
    }
}

class Dish {
    private final byte[] oneKb = new byte[1_024];
    private final int id;
    Dish(int id) {
        this.id = id;
        System.out.println("Created: " + id);
    }
    public String toString() {
        return String.valueOf(id);
    }
}
