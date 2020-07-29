package com.pandey.popcorn4.data.network.db

import com.pandey.popcorn4.movie.data.MovieInfo

class FvrtMovieDbObjectConverter {

    companion object {

        fun toDbObject(movieInfo: MovieInfo): FvrtMoviesDbObject? {
            movieInfo.movieLanguage
            movieInfo.movieTitle
            return FvrtMoviesDbObject(
                    movieInfo.movieId,
                    movieInfo.moviePoster!!,
                    movieInfo.movieTitle,
                    movieInfo.movieMovieDescription!!,
                    movieInfo.movieStar,
                    movieInfo.movieLanguage!!,
                    System.currentTimeMillis()
            )
        }

        fun fromDbObject(fvrtMoviesDbObject: FvrtMoviesDbObject): MovieInfo? {
            return object : MovieInfo {
                override fun getMovieId(): Int {
                    return fvrtMoviesDbObject.movieId
                }

                override fun getMoviePoster(): String {
                    return fvrtMoviesDbObject.moviePosterPath
                }

                override fun getMovieTitle(): String {
                    return fvrtMoviesDbObject.movieTitle!!
                }

                override fun getMovieStar(): Int {
                    return fvrtMoviesDbObject.movieStarCount
                }

                override fun getMovieLanguage(): String {
                    return fvrtMoviesDbObject.movieLanguage
                }

                override fun getMovieMovieDescription(): String {
                    return fvrtMoviesDbObject.movieDescription
                }
            }
        }
    }
}