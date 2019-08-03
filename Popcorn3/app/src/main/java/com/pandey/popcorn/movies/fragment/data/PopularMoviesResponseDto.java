package com.pandey.popcorn.movies.fragment.data;

import java.io.Serializable;
import java.util.List;

public class PopularMoviesResponseDto implements Serializable {

    private int pageNumber;

    private int totalResults;

    private int totalPages;

    private List<Movie> moviesList;

    public PopularMoviesResponseDto(int pageNumber, int totalResults, int totalPages, List<Movie> moviesList) {
        this.pageNumber = pageNumber;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.moviesList = moviesList;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<Movie> getMoviesList() {
        return moviesList;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setMoviesArray(List<Movie> moviesArray) {
        this.moviesList = moviesArray;
    }
}
