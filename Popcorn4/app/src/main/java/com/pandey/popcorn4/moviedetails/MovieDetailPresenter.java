package com.pandey.popcorn4.moviedetails;

import androidx.annotation.Nullable;

import com.pandey.popcorn4.AppConfig;
import com.pandey.popcorn4.data.network.RetrofitHelper;
import com.pandey.popcorn4.moviedetails.data.MovieDto;
import com.pandey.popcorn4.utils.DataUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class MovieDetailPresenter {

    @Nullable
    private MovieDetailView movieDetailView;

    MovieDetailPresenter(@Nullable MovieDetailView movieDetailView) {
        this.movieDetailView = movieDetailView;
    }

    void getMovieDetail(int movieId) {
        String baseUrl = AppConfig.getMovieBaseUrl() + movieId;
        RetrofitHelper.Companion
                .getApiService()
                .getMovieDetail(baseUrl, AppConfig.getMovieApiKey(), AppConfig.getAppendVideoWithResponse())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (movieDetailView != null) {
                        movieDetailView.onMovieDetailsFetching();
                    }
                })
                .doOnSuccess(jsonObject -> {
                    if (movieDetailView!= null) {
                        movieDetailView.onMovieDetailCompleted(DataUtils.parseJSON(jsonObject));
                    }
                })
                .doOnError(throwable -> {
                    if (movieDetailView!= null) {
                        movieDetailView.onMovieDetailFetchingFailed();
                    }
                })
                .subscribe();
    }

    public interface MovieDetailView {
        void onMovieDetailsFetching();
        void onMovieDetailFetchingFailed();
        void onMovieDetailCompleted(MovieDto movie);
    }
}
