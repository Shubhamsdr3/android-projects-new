package com.pandey.popcorn4.moviedetails;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.pandey.popcorn4.moviedetails.data.MovieResponseDto;
import com.pandey.popcorn4.utils.RetrofitHelper;

import java.lang.reflect.Type;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

class MovieDetailPresenter {

    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String MOVIE_API_KEY = "8d0bbe47677faff5e8d33e89d1aac537";

    @Nullable
    private MovieDetailView movieDetailView;

    @Nullable
    private MovieResponseDto movie;

    MovieDetailPresenter(@Nullable MovieDetailView movieDetailView) {
        this.movieDetailView = movieDetailView;
    }

    void getMovieDetail(int movieId) {

        // Fix it , not a right way to do.
        String baseUrl = MOVIE_BASE_URL + movieId;
        Observable<JsonObject> movieDetails =
                RetrofitHelper.getApiService().getMovieDetail(baseUrl, MOVIE_API_KEY);

        movieDetails
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Timber.i("Started fetching details...");
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        Timber.i("movie response %s", jsonObject);
                        movie =  parseJSON(jsonObject);
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


    private MovieResponseDto parseJSON(JsonObject jsonObject) {
        Gson gson = new Gson();
        Type type = new TypeToken<MovieResponseDto>(){}.getType();
        return gson.fromJson(jsonObject, type);
    }

    public interface MovieDetailView {
        void onMovieDetailCompleted(MovieResponseDto movie);
    }
}
