package com.pandey.popcorn4.moviedetails.data;

import java.io.Serializable;

import androidx.annotation.Nullable;

class MovieCollectionDto implements Serializable {

    private int id;

    @Nullable
    private String name;

    @Nullable
    private String poster_path;

    @Nullable
    private String backdrop_path;

    public int getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getPoster_path() {
        return poster_path;
    }

    @Nullable
    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public void setPoster_path(@Nullable String poster_path) {
        this.poster_path = poster_path;
    }

    public void setBackdrop_path(@Nullable String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }
}
