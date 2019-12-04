package com.pandey.popcorn4.movie;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pandey.popcorn4.AppConfig;
import com.pandey.popcorn4.base.BasePresenter;
import com.pandey.popcorn4.db.AppDatabase;
import com.pandey.popcorn4.db.FvrtMoviesDbObject;
import com.pandey.popcorn4.movie.data.MovieInfo;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;
import com.pandey.popcorn4.utils.DataUtils;
import com.pandey.popcorn4.utils.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

class PopularMoviePresenter extends BasePresenter {


    @NonNull
    private List<MoviesResponseDto> movieList = new ArrayList<>();

    @Nullable
    private PopularMovieView popularMovieView;


    @Nullable
    private MoviesResponseDto mMovieResponseDto;

    @NonNull
    private Context mContext;

    PopularMoviePresenter(@NonNull Context mContext, @Nullable PopularMovieView popularMovieView) {
        this.popularMovieView = popularMovieView;
        this.mContext = mContext;
        fetchLatestMovie();
    }

    void fetchPopularMovies() {
        RetrofitHelper
                .getApiService()
                .getPopularMovies(
                        AppConfig.getMovieBaseUrl() + "popular",
                        AppConfig.getMovieApiKey()
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (popularMovieView != null) {
                            popularMovieView.onPopularMovieFetching();
                        }
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        Timber.i("Json response %s", jsonObject);
                        JsonArray jsonArray = (JsonArray) jsonObject.get("results");
                        movieList.addAll(DataUtils.parseJSONArrayToList(jsonArray));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.i(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        handleResponse(movieList);
                    }
                });
    }

    private void fetchLatestMovie() {
        String latestMovieUrl = AppConfig.getMovieBaseUrl() + "latest";

        RetrofitHelper
                .getApiService()
                .getLatestMovie(
                        latestMovieUrl,
                        AppConfig.getEngLang(),
                        AppConfig.getMovieApiKey()
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MoviesResponseDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MoviesResponseDto moviesResponseDto) {
                        mMovieResponseDto = moviesResponseDto;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.i(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        if (popularMovieView != null && mMovieResponseDto != null) {
                            popularMovieView.onLatestMovieFetchSuccess(mMovieResponseDto);
                        }
                    }
                });
    }

    private void handleResponse(@NonNull List<MoviesResponseDto> movieList) {
        List<MovieInfo> movieInfoList = new ArrayList<>(movieList.size());

        for (MoviesResponseDto moviesResponseDto: movieList) {
            movieInfoList.add(new MovieInfo() {
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

                                  @NonNull
                                  @Override
                                  public String getMovieVoteCount() {
                                      return String.valueOf(moviesResponseDto.getVote_count());
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
            popularMovieView.onPopularMoviesFetched(movieInfoList);
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
        void onPopularMoviesFetched(List<MovieInfo> movieInfoList);
        void onLatestMovieFetchSuccess(@NonNull MoviesResponseDto moviesResponseDto);
    }
}

