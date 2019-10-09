package com.pandey.popcorn4.movie;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.pandey.popcorn4.base.BasePresenter;
import com.pandey.popcorn4.db.AppDatabase;
import com.pandey.popcorn4.db.FvrtMoviesDbObject;
import com.pandey.popcorn4.fvrtmovies.data.FvrtMovieDbObjectConverter;
import com.pandey.popcorn4.movie.data.MovieInfo;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;
import com.pandey.popcorn4.utils.RetrofitHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PopularMoviePresenter extends BasePresenter {

    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/popular";
    private static final String MOVIE_API_KEY = "8d0bbe47677faff5e8d33e89d1aac537";

    @NonNull
    private List<MoviesResponseDto> movieList = new ArrayList<>();

    @Nullable
    private PopularMovieView popularMovieView;

    @NonNull
    private Context mContext;

    PopularMoviePresenter(@NonNull Context mContext, @Nullable PopularMovieView popularMovieView) {
        this.popularMovieView = popularMovieView;
        this.mContext = mContext;
    }

    void fetchPopularMovies() {
        Observable<JsonObject> responseSingle =
                RetrofitHelper.getApiService().getPopularMovies(MOVIE_BASE_URL, MOVIE_API_KEY);

        responseSingle
                .subscribeOn(Schedulers.io())
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
                        movieList.addAll(parseJSON(jsonArray));
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

    public void saveToDb(@NonNull FvrtMoviesDbObject fvrtMoviesDbObject) {
        Completable.fromAction(
                () -> AppDatabase.appDatabase(mContext).fvrtMoviesDao().insert(fvrtMoviesDbObject))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    private List<MoviesResponseDto> parseJSON(JsonArray jsonArray) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MoviesResponseDto>>(){}.getType();
        return gson.fromJson(jsonArray, type);
    }

    public interface PopularMovieView {
        void onPopularMovieFetching();
        void onPopularMoviesFetched(List<MovieInfo> movieInfoList);
    }
}

