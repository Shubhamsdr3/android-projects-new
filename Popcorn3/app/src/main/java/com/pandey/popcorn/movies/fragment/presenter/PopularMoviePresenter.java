package com.pandey.popcorn.movies.fragment.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.pandey.popcorn.movies.fragment.data.Movie;
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

public class PopularMoviePresenter {

    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/popular";
    private static final String MOVIE_API_KEY = "8d0bbe47677faff5e8d33e89d1aac537";

    @Nullable
    private List<Movie> movieList;

    @Nullable
    private PopularMovieView popularMovieView;

    public PopularMoviePresenter(@Nullable PopularMovieView popularMovieView) {
        this.popularMovieView = popularMovieView;
    }

    public void fetchPopularMovies() {

        Observable<JsonObject> responseSingle =
                RetrofitHelper.getApiService().getPopularMovies(MOVIE_BASE_URL, MOVIE_API_KEY);

        responseSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Timber.i("Fetching moving starter...");
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        Timber.i("Json response %s", jsonObject);
                        JsonArray jsonArray = (JsonArray) jsonObject.get("results");
                        movieList = parseJSON(jsonArray);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.i(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        if (popularMovieView != null) {
                            popularMovieView.onPopularMoviesFetched(movieList);
                        }
                    }
                });

    }

    private List<Movie> parseJSON(JsonArray jsonArray) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Movie>>(){}.getType();
        return gson.fromJson(jsonArray, type);
    }

    public interface PopularMovieView {
       void onPopularMoviesFetched(List<Movie> movieList);
    }
}
