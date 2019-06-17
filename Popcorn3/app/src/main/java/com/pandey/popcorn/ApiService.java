package com.pandey.popcorn;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {

    @GET
    Observable<JsonObject> getPopularMovies(@Url String url,  @Query("api_key") String apiKey);


    @GET
    Observable<JsonObject> getTopHeadlines(@Url String url, @Query("country") String countryCode, @Query("apiKey") String apiKey);

}
