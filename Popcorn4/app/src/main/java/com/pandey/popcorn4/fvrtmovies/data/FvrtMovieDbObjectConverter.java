package com.pandey.popcorn4.fvrtmovies.data;

import androidx.annotation.NonNull;

import com.pandey.popcorn4.db.FvrtMoviesDbObject;
import com.pandey.popcorn4.movie.data.MovieInfo;

public class FvrtMovieDbObjectConverter {

    public static FvrtMoviesDbObject toDbObject(@NonNull MovieInfo movieInfo) {
        movieInfo.getMovieLanguage();
        movieInfo.getMovieTitle();
        return new FvrtMoviesDbObject(
                movieInfo.getMovieId(),
                movieInfo.getMoviePoster(),
                movieInfo.getMovieTitle(),
                movieInfo.getMovieMovieDescription(),
                movieInfo.getMovieStar(),
                movieInfo.getMovieLanguage(),
                System.currentTimeMillis()
        );
    }

    public static MovieInfo fromDbObject(FvrtMoviesDbObject fvrtMoviesDbObject) {
        return new MovieInfo() {
            @Override
            public int getMovieId() {
                return fvrtMoviesDbObject.movieId;
            }

            @NonNull
            @Override
            public String getMoviePoster() {
                return fvrtMoviesDbObject.moviePosterPath;
            }

            @NonNull
            @Override
            public String getMovieTitle() {
                return fvrtMoviesDbObject.movieTitle;
            }

            @Override
            public int getMovieStar() {
                return fvrtMoviesDbObject.movieStarCount;
            }

            @NonNull
            @Override
            public String getMovieLanguage() {
                return fvrtMoviesDbObject.movieLanguage;
            }

            @NonNull
            @Override
            public String getMovieMovieDescription() {
                return fvrtMoviesDbObject.movieDescription;
            }
        };
    }

}
