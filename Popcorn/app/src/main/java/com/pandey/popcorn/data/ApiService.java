package com.pandey.popcorn.data;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("movie/popular")
    public Observable<PopularMoviesResponseDto> getPopularMovies();
}
