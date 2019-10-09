package com.pandey.popcorn4.movie.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface MovieInfo {

    int getMovieId();

    @Nullable
    String getMoviePoster();

    @NonNull
    String getMovieTitle();

    @NonNull
    String getMovieVoteCount();

    @Nullable
    String getMovieLanguage();

    @Nullable
    String getMovieMovieDescription();

}
