package com.pandey.popcorn4.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonArray;
import com.pandey.popcorn4.AppConfig;
import com.pandey.popcorn4.data.network.RetrofitHelper;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;
import com.pandey.popcorn4.utils.DataUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MovieSearchPresenter  {

    private PublishSubject<String> mSearchString = PublishSubject.create();

    @Nullable
    private MovieSearchView movieSearchView;

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
        RetrofitHelper.Companion
                .getApiService()
                .getSearchedMovie(AppConfig.getMovieSearchUrl(), AppConfig.getEngLang(), AppConfig.getMovieApiKey(), text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (movieSearchView != null) {
                        movieSearchView.onMovieFetching();
                    }
                })
                .doOnSuccess(jsonObject -> {
                    JsonArray jsonArray = (JsonArray) jsonObject.get("results");
                    JsonArray emptyJson = new JsonArray();
                    // TODO: FIX ME : parse Date.
                    for (int i =0; i < jsonArray.size(); i++) {
                        if(jsonArray.get(i) != null && !jsonArray.get(i).toString().equals("")) {
                            emptyJson.add(jsonArray.get(i));
                        }
                    }
                    if (movieSearchView != null) {
                        movieSearchView.onMovieFetchedSuccess(DataUtils.parseJSONArrayToList(emptyJson));
                    }
                })
                .doOnError(throwable -> {
                    if (movieSearchView != null) {
                        movieSearchView.onMovieFetchedFailed();
                    }
                })
                .subscribe();
    }

    public interface MovieSearchView {
        void onMovieFetching();
        void onMovieFetchedSuccess(@NonNull List<MoviesResponseDto> moviesResponseDtoList);
        void onMovieFetchedFailed();
    }
}
