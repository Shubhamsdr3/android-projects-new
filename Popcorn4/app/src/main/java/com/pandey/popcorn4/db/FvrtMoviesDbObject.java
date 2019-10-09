package com.pandey.popcorn4.db;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.common.annotation.KeepName;

@Entity
@Keep
@KeepName
public class FvrtMoviesDbObject {

    @PrimaryKey
    @ColumnInfo(name = "movieId")
    public int movieId;

    @ColumnInfo(name = "moviePoster")
    public String moviePosterPath;

    @ColumnInfo(name = "movieTitle")
    public String movieTitle;

    @ColumnInfo(name = "movieDescription")
    public String movieDescription;

    @ColumnInfo(name = "voteCount")
    public int movieVoteCount;

    @ColumnInfo(name = "language")
    public String movieLanguage;

    @ColumnInfo(name = "createdAt")
    public long timestamp;

    public FvrtMoviesDbObject(int movieId,
                              @Nullable String moviePosterPath,
                              @NonNull String movieTitle,
                              @Nullable String movieDescription,
                              int movieVoteCount,
                              @Nullable String movieLanguage,
                              long timestamp) {
        this.movieId = movieId;
        this.moviePosterPath = moviePosterPath;
        this.movieDescription = movieDescription;
        this.movieTitle = movieTitle;
        this.movieVoteCount = movieVoteCount;
        this.movieLanguage = movieLanguage;
        this.timestamp = timestamp;
    }
}
