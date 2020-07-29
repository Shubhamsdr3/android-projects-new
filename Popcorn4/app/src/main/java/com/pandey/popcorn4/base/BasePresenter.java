package com.pandey.popcorn4.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter {

    private CompositeDisposable mDisposables;

    protected BasePresenter() {
        mDisposables =  new CompositeDisposable();
        onLoad();
    }

    public void onLoad() {

    }

    protected final void addDisposables(Disposable... disposables) {
        mDisposables.addAll(disposables);
    }
}
