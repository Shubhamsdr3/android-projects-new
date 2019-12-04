package com.pandey.popcorn4.moviedetails.data;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pandey.popcorn4.mediaplayer.data.VideoResponseDto;

public class MovieDto implements Serializable {

    private boolean adult;

    @Nullable
    private String backdropPath;

    @Nullable
    private MovieCollectionDto belongsToCollection;

    private int budget;

    private MovieGenresDto[] genres;

    @Nullable
    private String homepage;

    private int id;

    @Nullable
    private String imdb_id;

    @Nullable
    private String original_language;

    @Nullable
    private String original_title;

    @Nullable
    private String overview;

    private float popularity;

    @Nullable
    private String poster_path;

    @Nullable
    private ProductionCountriesDto[] production_countries;

    @Nullable
    private ProductionCompaniesDto[] production_companies;

    @Nullable
    private String release_date;

    private int revenue;

    private int runtime;

    @Nullable
    private SpokenLanguagesDto[] spoken_languages;

    @Nullable
    private String status;

    @Nullable
    private String tagline;

    @Nullable
    private String title;
    private boolean video;
    private int vote_average;
    private int vote_count;

    @Nullable
    private VideoResponseDto videos;

    @Nullable
    public VideoResponseDto getVideos() {
        return videos;
    }

    public void setVideos(@NonNull VideoResponseDto videos) {
        this.videos = videos;
    }

    @Nullable
    public String getPoster_path() {
        return poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    @Nullable
    public String getBackdropPath() {
        return backdropPath;
    }

    @Nullable
    public MovieCollectionDto getBelongsToCollection() {
        return belongsToCollection;
    }

    public int getBudget() {
        return budget;
    }

    public MovieGenresDto[] getGenres() {
        return genres;
    }

    @Nullable
    public String getHomepage() {
        return homepage;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public String getImdb_id() {
        return imdb_id;
    }

    @Nullable
    public String getOriginal_language() {
        return original_language;
    }

    @Nullable
    public String getOriginal_title() {
        return original_title;
    }

    @Nullable
    public String getOverview() {
        return overview;
    }

    public float getPopularity() {
        return popularity;
    }

    @Nullable
    public String getPosterPath() {
        return poster_path;
    }

    @Nullable
    public ProductionCountriesDto[] getProduction_countries() {
        return production_countries;
    }

    @Nullable
    public ProductionCompaniesDto[] getProduction_companies() {
        return production_companies;
    }

    @Nullable
    public String getRelease_date() {
        return release_date;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    @Nullable
    public SpokenLanguagesDto[] getSpoken_languages() {
        return spoken_languages;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    @Nullable
    public String getTagline() {
        return tagline;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public int getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setBackdropPath(@Nullable String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setBelongsToCollection(@Nullable MovieCollectionDto belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setGenres(MovieGenresDto[] genres) {
        this.genres = genres;
    }

    public void setHomepage(@Nullable String homepage) {
        this.homepage = homepage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImdb_id(@Nullable String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public void setOriginal_language(@Nullable String original_language) {
        this.original_language = original_language;
    }

    public void setOriginal_title(@Nullable String original_title) {
        this.original_title = original_title;
    }

    public void setOverview(@Nullable String overview) {
        this.overview = overview;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setPoster_path(@Nullable String poster_path) {
        this.poster_path = poster_path;
    }

    public void setProduction_countries(@Nullable ProductionCountriesDto[] production_countries) {
        this.production_countries = production_countries;
    }

    public void setProduction_companies(@Nullable ProductionCompaniesDto[] production_companies) {
        this.production_companies = production_companies;
    }

    public void setRelease_date(@Nullable String release_date) {
        this.release_date = release_date;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setSpoken_languages(@Nullable SpokenLanguagesDto[] spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public void setStatus(@Nullable String status) {
        this.status = status;
    }

    public void setTagline(@Nullable String tagline) {
        this.tagline = tagline;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setVote_average(int vote_average) {
        this.vote_average = vote_average;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}
