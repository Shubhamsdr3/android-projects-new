package com.pandey.popcorn4.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pandey.popcorn4.AppConfig;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;
import com.pandey.popcorn4.utils.DataUtils;
import com.pandey.popcorn4.utils.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class MovieSearchPresenter  {

    private PublishSubject<String> mSearchString = PublishSubject.create();

    @Nullable
    private MovieSearchView movieSearchView;

    @NonNull
    private List<MoviesResponseDto> movieList = new ArrayList<>();

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
        RetrofitHelper
                .getApiService()
                .getSearchedMovie(
                        AppConfig.getMovieSearchUrl(),
                        AppConfig.getEngLang(),
                        AppConfig.getMovieApiKey(),
                        text
                ).subscribeOn(Schedulers.io())
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
                        JsonArray emptyJson = new JsonArray();
                        // TODO: FIX ME : parse Date.
                        for (int i =0; i < jsonArray.size(); i++) {
                            if(jsonArray.get(i) != null && !jsonArray.get(i).toString().equals("")) {
                                emptyJson.add(jsonArray.get(i));
                            }
                        }
                        movieList.addAll(DataUtils.parseJSONArrayToList(emptyJson));
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

    private void makeDbCall() {

    }

    public interface MovieSearchView {
        void onMovieFetching();
        void onMovieFetchedSuccess(@NonNull List<MoviesResponseDto> moviesResponseDtoList);
        void onMovieFetchedFailed();
    }
}
