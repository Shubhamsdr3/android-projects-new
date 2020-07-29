package com.pandey.popcorn4.moviedetails.data;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pandey.popcorn4.mediaplayer.data.VideoResponseDto;

public class MovieDto implements Serializable {

    private boolean adult;

    private int budget;

    private int id;

    @Nullable
    private String backdropPath;

    @Nullable
    private MovieCollectionDto belongsToCollection;

    private List<MovieGenresDto> genres;

    @Nullable
    private String homepage;

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
    private List<ProductionCompaniesDto> production_companies;

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

    public List<MovieGenresDto> getGenres() {
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
    public List<ProductionCompaniesDto> getProduction_companies() {
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

}
