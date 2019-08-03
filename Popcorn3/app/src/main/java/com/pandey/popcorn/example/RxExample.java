package com.pandey.popcorn.example;

import com.pandey.popcorn.news.data.Article;
import com.pandey.popcorn.news.data.presenter.NewsPresenter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class RxExample implements NewsPresenter.NewsView {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Observable<String> observableObject;

    private RxExample(Observable<String> observable) {
        this.observableObject = observable;
    }

    public void init() {
        compositeDisposable.add(observableObject.subscribe(new Consumer<String>() {
            @Override public void accept(String s) throws Exception {
//                NewsPresenter newsPresenter = new NewsPresenter(this);
//                newsPresenter.getNewsHeadlines();
                System.out.println();
            }
        }));
    }

    public void deinit() {
        compositeDisposable.dispose();
    }

    public static void main(String[] args) {
        Observable<String> observable = Observable.fromArray("Hello", "Hi");
       RxExample rxExample =  new RxExample(observable);
        rxExample.init();
        // Do your stuff.
        rxExample.deinit();
    }

    @Override
    public void onNewsFeedFetchingStarted() {

    }

    @Override
    public void onSuccessfullyFetchedArticle(List<Article> articleList) {

    }
}
