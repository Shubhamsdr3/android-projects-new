package com.pandey.popcorn.movies.fragment.data;

import java.io.Serializable;
import java.util.Date;

import androidx.annotation.Nullable;

public class Movie implements Serializable {

    @Nullable
    private String poster_path;

    private int id;

    @Nullable
    private String title;

    private int vote_count;

    @Nullable
    private String original_title;

    private boolean video;

    private float vote_average;

    private float popularity;

    @Nullable
    private String original_language;

    @Nullable
    private int[] genre_ids;

    @Nullable
    private String backdrop_path;

    private boolean adult;

    @Nullable
    private String overview;

    @Nullable
    private Date release_date;

    @Nullable
    public String getPoster_path() {
        return poster_path;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public int getVote_count() {
        return vote_count;
    }

    @Nullable
    public String getOriginal_title() {
        return original_title;
    }


    public boolean isVideo() {
        return video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public float getPopularity() {
        return popularity;
    }

    @Nullable
    public String getOriginal_language() {
        return original_language;
    }

    @Nullable
    public int[] getGenre_ids() {
        return genre_ids;
    }

    @Nullable
    public String getBackdrop_path() {
        return backdrop_path;
    }

    public boolean isAdult() {
        return adult;
    }

    @Nullable
    public String getOverview() {
        return overview;
    }

    @Nullable
    public Date getRelease_date() {
        return release_date;
    }

    public void setPoster_path(@Nullable String poster_path) {
        this.poster_path = poster_path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public void setOriginal_title(@Nullable String original_title) {
        this.original_title = original_title;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setOriginal_language(@Nullable String original_language) {
        this.original_language = original_language;
    }

    public void setGenre_ids(@Nullable int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public void setBackdrop_path(@Nullable String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setOverview(@Nullable String overview) {
        this.overview = overview;
    }

    public void setRelease_date(@Nullable Date release_date) {
        this.release_date = release_date;
    }
}
