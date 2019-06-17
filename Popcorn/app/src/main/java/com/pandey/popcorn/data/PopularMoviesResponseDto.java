package com.pandey.popcorn.data;

import java.util.Date;

public class PopularMoviesResponseDto {

    private int movieId;

    private int voteCount;

    private boolean video;

    private float avgVoting;

    private String movieTitle;

    private float popularity;

    private String posterPath;

    private String originalLanguage;

    private int[] genereIds;

    private String backDropPath;

    private boolean adult;

    private String movieOverview;

    private Date releaseDate;


    public PopularMoviesResponseDto(int movieId, int voteCount, boolean video, float avgVoting, String movieTitle, float popularity, String posterPath, String originalLanguage, int[] genereIds, String backDropPath, boolean adult, String movieOverview, Date releaseDate) {
        this.movieId = movieId;
        this.voteCount = voteCount;
        this.video = video;
        this.avgVoting = avgVoting;
        this.movieTitle = movieTitle;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.genereIds = genereIds;
        this.backDropPath = backDropPath;
        this.adult = adult;
        this.movieOverview = movieOverview;
        this.releaseDate = releaseDate;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public float getAvgVoting() {
        return avgVoting;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public int[] getGenereIds() {
        return genereIds;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setAvgVoting(float avgVoting) {
        this.avgVoting = avgVoting;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setGenereIds(int[] genereIds) {
        this.genereIds = genereIds;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
