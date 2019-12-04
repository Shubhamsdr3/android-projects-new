package com.pandey.popcorn4;

import com.google.gson.JsonObject;
import com.pandey.popcorn4.mediaplayer.data.VideoResponseDto;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {

    //TODO: remove taking URL as parameter.
    @GET
    Observable<JsonObject> getPopularMovies(@Url String url,
                                            @Query("api_key") String apiKey);

    @GET
    Observable<JsonObject> getMovieDetail(@Url String url,
                                          @Query("api_key") String apiKey,
                                          @Query("append_to_response") String responseType);

    @GET
    Observable<JsonObject> getTopHeadlines(@Url String url,
                                           @Query("country") String countryCode,
                                           @Query("apiKey") String apiKey);

    @GET
    Observable<JsonObject> getSearchedMovie(@Url String url,
                                            @Query("language") String language,
                                            @Query("api_key") String  apiKey,
                                            @Query("query") String  query);

    @GET
    Observable<MoviesResponseDto> getLatestMovie(@Url String url,
                                            @Query("language") String language,
                                            @Query("api_key") String  apiKey);

    @GET
    Observable<VideoResponseDto> getMovieTrailer(@Url String url,
                                                      @Query("language") String language,
                                                      @Query("api_key") String  apiKey);

}

