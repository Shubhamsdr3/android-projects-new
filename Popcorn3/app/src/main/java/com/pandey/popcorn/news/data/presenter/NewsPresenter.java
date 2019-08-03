package com.pandey.popcorn.news.data.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.pandey.popcorn.news.data.Article;
import com.pandey.popcorn.utils.RetrofitHelper;

import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class NewsPresenter  {

    @Nullable
    private List<Article> articleList;

    @Nullable
    private NewsView newsView;

    private static  final String NEWS_API_KEY = "f3d7fd0be82b432abd0da10f1fbc833e";
    private static final String NEWS_BASE_URL = "https://newsapi.org/v2/top-headlines";
    private static final String COUNTRY_CODE = "us";

    public NewsPresenter(@Nullable NewsView newsView) {
        this.newsView = newsView;
    }


    public void getNewsHeadlines() {

        Timber.i("fetching data from network....");
        Observable<JsonObject> responseSingle =
                RetrofitHelper.getApiService().getTopHeadlines(NEWS_BASE_URL, COUNTRY_CODE, NEWS_API_KEY);

        responseSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (newsView != null) {
                            newsView.onNewsFeedFetchingStarted();
                        }
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        Timber.i("Json response %s", jsonObject);
                        JsonArray jsonArray = (JsonArray) jsonObject.get("articles");
                        articleList =  parseJSON(jsonArray);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.i(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Timber.i("OnComplete is called...");
                        if (newsView != null) {
                            newsView.onSuccessfullyFetchedArticle(articleList);
                        }
                    }
                });
    }

    private List<Article> parseJSON(JsonArray jsonArray) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Article>>(){}.getType();
        return gson.fromJson(jsonArray, type);
    }


    public interface NewsView {
        void onNewsFeedFetchingStarted();
        void onSuccessfullyFetchedArticle(List<Article> articleList);
    }

}
