package com.pandey.popcorn4.search;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;
import com.pandey.popcorn4.utils.RetrofitHelper;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class MovieSearchPresenter  {

    private PublishSubject<String> mSearchString = PublishSubject.create();

    private CompositeDisposable disposable = new CompositeDisposable();

    @Nullable
    private MovieSearchView movieSearchView;

    @Nullable
    private List<MoviesResponseDto> movieList;

    private static final String MOVIE_SEARCH_URL = "https://api.themoviedb.org/3/search/movie?page=1";
    private final static String MOVIE_LANGUAGE = "en-US";
    private final static String API_KEY = "8d0bbe47677faff5e8d33e89d1aac537";

    MovieSearchPresenter(@Nullable MovieSearchView movieSearchView) {
        this.movieSearchView = movieSearchView;
        onLoad();
    }

    void fetchSearchedMovie(@Nullable String text) {
        mSearchString.onNext(text);
    }

    private void onLoad() {
        mSearchString.debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(query -> {
                    makeNetworkCall(query);
                    return "Kuchh bhi";
                })
                .subscribe();
    }

    private void makeNetworkCall(@NonNull String text) {

        Observable<JsonObject> searchedMovie =
                RetrofitHelper.getApiService().getSearchedMovie(MOVIE_SEARCH_URL, MOVIE_LANGUAGE, API_KEY, text);

        searchedMovie
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Timber.i("Fetching moving starter...");
                        if (movieSearchView != null) {
                            movieSearchView.onMovieFetching();
                        }
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
                        if (movieSearchView != null) {
                            movieSearchView.onMovieFetchedSuccess(movieList);
                        }
                    }
                });
    }

    private List<MoviesResponseDto> parseJSON(JsonArray jsonArray) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MoviesResponseDto>>(){}.getType();
        return gson.fromJson(jsonArray, type);
    }

    public interface MovieSearchView {
        void onMovieFetching();
        void onMovieFetchedSuccess(@Nullable List<MoviesResponseDto> moviesResponseDtoList);
        void onMovieFetchedFailed();
    }
}
