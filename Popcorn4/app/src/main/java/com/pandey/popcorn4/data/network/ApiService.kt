package com.pandey.popcorn4.data.network

import com.google.gson.JsonObject
import com.pandey.popcorn4.appdata.ConfigResponseDto
import com.pandey.popcorn4.mediaplayer.data.VideoResponseDto
import com.pandey.popcorn4.movie.data.MoviesResponseDto
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

     //TODO: remove taking URL as parameter.
     @GET
     fun getAppConfig(@Url url: String?,
                         @Query("api_key") apiKey: String?): Single<ConfigResponseDto?>

    @GET
    fun getPopularMovies(@Url url: String?,
                             @Query("api_key") apiKey: String?): Single<JsonObject?>

    @GET
    fun getMovieDetail(@Url url: String?,
                       @Query("api_key") apiKey: String?,
                       @Query("append_to_response") responseType: String?): Single<JsonObject?>

    @GET
    fun getTopHeadlines(@Url url: String?,
                        @Query("country") countryCode: String?,
                        @Query("apiKey") apiKey: String?): Single<JsonObject?>

    @GET
    fun getSearchedMovie(@Url url: String?,
                         @Query("language") language: String?,
                         @Query("api_key") apiKey: String?,
                         @Query("query") query: String?): Single<JsonObject?>

    @GET
    fun getLatestMovie(@Url url: String?,
                       @Query("language") language: String?,
                       @Query("api_key") apiKey: String?): Single<MoviesResponseDto?>

    @GET
    fun getMovieTrailer(@Url url: String?,
                        @Query("language") language: String?,
                        @Query("api_key") apiKey: String?): Single<VideoResponseDto?>

}