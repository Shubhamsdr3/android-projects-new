package com.pandey.popcorn4.fvrtmovies;

import android.content.Context;

import androidx.annotation.NonNull;

import com.pandey.popcorn4.base.BasePresenter;
import com.pandey.popcorn4.data.network.db.FvrtMovieDbObjectConverter;
import com.pandey.popcorn4.data.network.db.FvrtMoviesDbObject;
import com.pandey.popcorn4.db.AppDatabase;
import com.pandey.popcorn4.movie.data.MovieInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

class FvrtMoviePresenter extends BasePresenter {

    @NonNull
    private Context mContext;

    @NonNull
    private FvrtMovieView mFvrtMovieView;

    public FvrtMoviePresenter(@NonNull Context context,
                              @NonNull FvrtMovieView fvrtMovieView) {
        this.mContext = context;
        this.mFvrtMovieView = fvrtMovieView;
    }

    void fetchFromDb() {
        addDisposables(
                AppDatabase.appDatabase(mContext).fvrtMoviesDao().getAllFvrtMovies()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(fvrtMoviesDbObjects -> {
                            List<MovieInfo> movieInfoList = new ArrayList<>();
                            for (FvrtMoviesDbObject object : fvrtMoviesDbObjects) {
                                movieInfoList.add(FvrtMovieDbObjectConverter.Companion.fromDbObject(object));
                            }
                            mFvrtMovieView.onDbFetchSuccess(movieInfoList);
                        }, Timber::e)
                );
    }

    public interface FvrtMovieView {
        void onDbFetchSuccess(@NonNull List<MovieInfo> movieInfoList);
    }
}
