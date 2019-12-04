package com.pandey.popcorn4.moviedetails;

import androidx.annotation.Nullable;

import com.google.gson.JsonObject;
import com.pandey.popcorn4.AppConfig;
import com.pandey.popcorn4.moviedetails.data.MovieDto;
import com.pandey.popcorn4.utils.DataUtils;
import com.pandey.popcorn4.utils.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

class MovieDetailPresenter {

    @Nullable
    private MovieDetailView movieDetailView;

    @Nullable
    private MovieDto movie;

    MovieDetailPresenter(@Nullable MovieDetailView movieDetailView) {
        this.movieDetailView = movieDetailView;
    }

    void getMovieDetail(int movieId) {
        String baseUrl = AppConfig.getMovieBaseUrl() + movieId;
        RetrofitHelper
                .getApiService()
                .getMovieDetail(
                        baseUrl,
                        AppConfig.getMovieApiKey(),
                        AppConfig.getAppendVideoWithResponse()
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Timber.i("Started fetching details...");
                        if (movieDetailView != null) {
                            movieDetailView.onMovieDetailsFetching();
                        }
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        Timber.i("movie response %s", jsonObject);
                        movie =  DataUtils.parseJSON(jsonObject);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {
                        if (movieDetailView!= null) {
                            movieDetailView.onMovieDetailCompleted(movie);
                        }
                    }
                });
    }


    public interface MovieDetailView {
        void onMovieDetailsFetching();
        void onMovieDetailCompleted(MovieDto movie);
    }
}
