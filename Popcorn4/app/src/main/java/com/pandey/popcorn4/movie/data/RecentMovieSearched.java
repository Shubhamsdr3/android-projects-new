package com.pandey.popcorn4.movie.data;

import androidx.annotation.Nullable;

import java.util.Date;

public class RecentMovieSearched {

    private int id;

    private float popularity;

    private boolean adult;

    @Nullable
    private String poster_path;

    @Nullable
    private String title;

    @Nullable
    private String original_language;

    @Nullable
    private String overview;

    @Nullable
    private Date release_date;

    public RecentMovieSearched(int id, float popularity, boolean adult,
                               @Nullable String poster_path, @Nullable String title,
                               @Nullable String original_language, @Nullable String overview,
                               @Nullable Date release_date) {
        this.id = id;
        this.popularity = popularity;
        this.adult = adult;
        this.poster_path = poster_path;
        this.title = title;
        this.original_language = original_language;
        this.overview = overview;
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public float getPopularity() {
        return popularity;
    }

    public boolean isAdult() {
        return adult;
    }

    @Nullable
    public String getPoster_path() {
        return poster_path;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getOriginal_language() {
        return original_language;
    }

    @Nullable
    public String getOverview() {
        return overview;
    }

    @Nullable
    public Date getRelease_date() {
        return release_date;
    }
}
