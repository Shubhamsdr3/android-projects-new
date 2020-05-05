package com.pandey.popcorn4.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import com.pandey.popcorn4.AppConfig
import com.pandey.popcorn4.data.network.ApiService
import com.pandey.popcorn4.data.network.RetrofitHelper.Companion.getApiService
import com.pandey.popcorn4.movie.data.MovieInfo
import com.pandey.popcorn4.movie.data.MoviesResponseDto
import com.pandey.popcorn4.utils.DataUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class PopularMovieRepository {

    private var latestMovieLiveData = MutableLiveData<MoviesResponseDto>()

    private var popularMovieList = MutableLiveData<List<MoviesResponseDto>>()

    companion object {
        const val POPULAR_KEY = "popular"
        const val LATEST_KEY = "latest"

        private var instance : PopularMovieRepository? = null

        fun getInstance(apiService: ApiService?) = run {
            instance
                    ?: synchronized(this) {
                        instance ?:
                        apiService?.let {
                                    PopularMovieRepository()
                                            .also {
                                                instance = it
                                            }
                                }
                    }
        }
    }

    fun getPopularMovieList(): LiveData<List<MoviesResponseDto>> {
        return popularMovieList;
    }

    fun getLatestPopularMovie(): LiveData<MoviesResponseDto> {
        return latestMovieLiveData
    }

    fun fetchPopularMoviesFromNetwork() {
        getApiService()
                .getPopularMovies(
                        AppConfig.getMovieBaseUrl() + POPULAR_KEY, AppConfig.getMovieApiKey()
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {

                }
                .doOnError {

                }
                .doOnSuccess {
                    val jsonArray =  it?.get("results") as JsonArray
                    popularMovieList.value = DataUtils.parseJSONArrayToList(jsonArray)
                }
                .subscribe()
    }

    private fun fetchLatestMovie() {
        val latestMovieUrl = AppConfig.getMovieBaseUrl() + LATEST_KEY
        getApiService()
                .getLatestMovie(latestMovieUrl, AppConfig.getEngLang(), AppConfig.getMovieApiKey()
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {

                }
                .doOnSuccess {
                    latestMovieLiveData.value = it
                }
                .doOnError {

                }
                .subscribe()
    }

    private fun handleResponse(movieList: List<MoviesResponseDto>) {
        val movieInfoList: MutableList<MovieInfo> = ArrayList(movieList.size)
        for (moviesResponseDto in movieList) {
            movieInfoList.add(
                    object : MovieInfo {
                        override fun getMovieId(): Int {
                            return moviesResponseDto.id
                        }

                        override fun getMoviePoster(): String? {
                            return moviesResponseDto.poster_path
                        }

                        override fun getMovieTitle(): String {
                            return moviesResponseDto.title
                        }

                        override fun getMovieStar(): Int {
                            return (moviesResponseDto.vote_average / 2).toInt()
                        }

                        override fun getMovieLanguage(): String? {
                            return moviesResponseDto.original_language
                        }

                        override fun getMovieMovieDescription(): String? {
                            return moviesResponseDto.overview
                        }
                    })
        }
//        if (popularMovieView != null) {
//            popularMovieView.onPopularMoviesFetched(movieInfoList)
//        }
    }
}