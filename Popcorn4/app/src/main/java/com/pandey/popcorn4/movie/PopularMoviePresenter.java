package com.pandey.popcorn4.movie;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonArray;
import com.pandey.popcorn4.AppConfig;
import com.pandey.popcorn4.base.BasePresenter;
import com.pandey.popcorn4.data.network.RetrofitHelper;
import com.pandey.popcorn4.data.network.db.FvrtMoviesDbObject;
import com.pandey.popcorn4.db.AppDatabase;
import com.pandey.popcorn4.movie.data.MovieInfo;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;
import com.pandey.popcorn4.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class PopularMoviePresenter extends BasePresenter {

    @Nullable
    private PopularMovieView popularMovieView;

    @NonNull
    private Context mContext;

    PopularMoviePresenter(@NonNull Context mContext, @Nullable PopularMovieView popularMovieView) {
        this.popularMovieView = popularMovieView;
        this.mContext = mContext;
    }

    @Override
    public void onLoad() {
        super.onLoad();
//        fetchLatestMovie();
    }

    void fetchPopularMovies() {
        RetrofitHelper.Companion
                .getApiService()
                .getPopularMovies(AppConfig.getMovieBaseUrl() + "popular", AppConfig.getMovieApiKey())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (popularMovieView != null) {
                        popularMovieView.onPopularMovieFetching();
                    }
                }).doOnError(throwable -> {
                    if (popularMovieView != null) {
                        popularMovieView.onPopularMovieFetchingFailed();
                    }
                }).doOnSuccess( jsonObject -> {
                    if (popularMovieView != null) {
                        JsonArray jsonArray = (JsonArray) jsonObject.get("results");
                        handleResponse(DataUtils.parseJSONArrayToList(jsonArray));
                    }
                }).subscribe();
    }

    private void fetchLatestMovie() {
        String latestMovieUrl = AppConfig.getMovieBaseUrl() + "latest";
        RetrofitHelper.Companion
                .getApiService()
                .getLatestMovie(latestMovieUrl, AppConfig.getEngLang(), AppConfig.getMovieApiKey()
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {

                }).doOnSuccess(moviesResponseDto -> {
                    if (popularMovieView != null) {
                        popularMovieView.onLatestMovieFetchSuccess(moviesResponseDto);
                    }
                }).doOnError(throwable -> {

                }).subscribe();
    }

    private void handleResponse(@NonNull List<MoviesResponseDto> movieList) {
        List<MovieInfo> movieInfoList = new ArrayList<>(movieList.size());

        for (MoviesResponseDto moviesResponseDto: movieList) {

            movieInfoList.add (
                    new MovieInfo() {
                        @Override
                        public int getMovieId() {
                            return moviesResponseDto.getId();
                        }

                        @Nullable
                        @Override
                        public String getMoviePoster() {
                            return moviesResponseDto.getPoster_path();
                        }

                        @NonNull
                        @Override
                        public String getMovieTitle() {
                            return moviesResponseDto.getTitle();
                        }

                        @Override
                        public int getMovieStar() {
                            return (int) (moviesResponseDto.getVote_average() / 2);
                        }

                        @Nullable
                        @Override
                        public String getMovieLanguage() {
                            return moviesResponseDto.getOriginal_language();
                        }

                        @Nullable
                        @Override
                        public String getMovieMovieDescription() {
                            return moviesResponseDto.getOverview();
                        }
                    });
        }

        if (popularMovieView != null) {
            popularMovieView.onPopularMoviesFetchingSuccess(movieInfoList);
        }
    }

    void saveToDb(@NonNull FvrtMoviesDbObject fvrtMoviesDbObject) {
        Completable.fromAction(
                () -> AppDatabase.appDatabase(mContext).fvrtMoviesDao().insert(fvrtMoviesDbObject))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public interface PopularMovieView {
        void onPopularMovieFetching();
        void onPopularMovieFetchingFailed();
        void onPopularMoviesFetchingSuccess(List<MovieInfo> movieInfoList);
        void onLatestMovieFetchSuccess(@NonNull MoviesResponseDto moviesResponseDto);
    }
}

