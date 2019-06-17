package com.pandey.popcorn4.rxexample;

import android.annotation.SuppressLint;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class StockExample {

    Observable<BigDecimal> pricesOf() {
        return Observable
                .interval(50, MILLISECONDS)
                .flatMap(this::randomDelay)
                .map(this::randomStockPrice)
                .map(BigDecimal::valueOf);
    }

    private Observable<Long> randomDelay(long x) {
        return Observable
                .just(x)
                .delay((long) (Math.random() * 100), MILLISECONDS);
    }

    private double randomStockPrice(long x) {
        return 100 + Math.random() * 10 +
                (Math.sin(x / 100.0)) * 60.0;
    }

    public static void main(String[] args) {
        List<BigDecimal> bigDecimals =  new ArrayList<>();
        StockExample stockExample = new StockExample();
       stockExample.pricesOf()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<BigDecimal>() {
                   @Override
                   public void onSubscribe(Disposable d) {
                       Timber.i("Just started...");
                   }

                   @Override
                   public void onNext(BigDecimal bigDecimal) {
                       Timber.i("This is the element..%s", bigDecimal);
                       bigDecimals.add(bigDecimal);
                   }

                   @Override
                   public void onError(Throwable e) {
                       Timber.i("Oops!");
                   }

                   @Override
                   public void onComplete() {
                       Timber.i("This is the result..%s", bigDecimals);
                   }
               });
    }
}
