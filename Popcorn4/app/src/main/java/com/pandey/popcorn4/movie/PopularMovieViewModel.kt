package com.pandey.popcorn4.movie

import androidx.lifecycle.LiveData
import com.pandey.popcorn4.base.BaseViewModel
import com.pandey.popcorn4.data.network.RetrofitHelper
import com.pandey.popcorn4.movie.data.MoviesResponseDto

class PopularMovieViewModel : BaseViewModel() {

    private var popularMovieRepository: PopularMovieRepository? = null

    init {
        popularMovieRepository = PopularMovieRepository.getInstance(RetrofitHelper.getApiService())
    }

    fun getPopularMovies(): LiveData<List<MoviesResponseDto>>? {
        return popularMovieRepository?.getPopularMovieList()
    }

    fun getLatestMovies(): LiveData<MoviesResponseDto>? {
        return popularMovieRepository?.getLatestPopularMovie()
    }
}