package com.pandey.popcorn4.rxexample;

import com.pandey.popcorn4.rxexample.data.Cities;
import com.pandey.popcorn4.rxexample.data.SearchResults;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeetUpApiService {

    @GET("/2/cities")
    Observable<Cities> listCities(@Query("lat") double lat, @Query("lon") double lon);

    @GET("/searchJSON")
    Observable<SearchResults> search(
            @Query("q") String query,
            @Query("maxRows") int maxRows,
            @Query("style") String style,
            @Query("username") String username);
}
