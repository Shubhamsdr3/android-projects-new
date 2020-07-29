package com.pandey.popcorn4.movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandey.popcorn4.R
import com.pandey.popcorn4.base.BaseFragmentNew
import com.pandey.popcorn4.base.InteractionListener
import com.pandey.popcorn4.camera.PhotoCaptureActivity
import com.pandey.popcorn4.movie.SwipeToDismissCallback.SwipeToDismissListener
import com.pandey.popcorn4.movie.data.MovieInfo
import com.pandey.popcorn4.movie.data.MoviesResponseDto
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import timber.log.Timber

class PopularMovieFragment : BaseFragmentNew() ,
        PopularMoviePresenter.PopularMovieView , PopularMoviesAdapter.PopularMovieAdapterCallback , SwipeToDismissListener {

    private lateinit var popularMovieFragmentListener: PopularMovieFragmentListener

    private var movieList = mutableListOf<MovieInfo>()

    companion object {
        fun newInstance() : PopularMovieFragment {
            return PopularMovieFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularMovieFragmentListener = getCommunicator() as PopularMovieFragmentListener
        popular_loading_animation.visibility = View.GONE

        val popularMoviePresenter = PopularMoviePresenter(context!!, this)
        popularMoviePresenter.fetchPopularMovies()

        val swipeToDismissCallback = SwipeToDismissCallback(activity!!, this)
        val itemTouchHelper = ItemTouchHelper(swipeToDismissCallback)
        itemTouchHelper.attachToRecyclerView(popular_movie_list)

        movie_search_bar.setOnClickListener {
            startActivity(Intent(activity, PhotoCaptureActivity::class.java))
        }
    }

    override fun getLayoutFile(): Int {
        return R.layout.fragment_popular_movies
    }

    override fun onPopularMovieFetchingFailed() {

    }

    override fun onPopularMoviesFetchingSuccess(movieInfoList: MutableList<MovieInfo>?) {
        Timber.d("The movie list received...%s", movieInfoList?.size)
        if (movieInfoList != null) {
            this.movieList.addAll(movieInfoList)
        }
        val mPopularMoviesAdapter = PopularMoviesAdapter(movieList, this)
        popular_movie_list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        popular_movie_list.adapter = mPopularMoviesAdapter
    }

    override fun onPopularMovieFetching() {

    }

    override fun onLatestMovieFetchSuccess(moviesResponseDto: MoviesResponseDto) {

    }

//    override fun onAdapterItemClick(view: View, movieInfo: MovieInfo) {
//        popularMovieFragmentListener.onMovieClicked(view, movieInfo.movieId)
//    }

    override fun onSwipedItem(position: Int) {
    }

    interface PopularMovieFragmentListener : InteractionListener {
        fun onMovieClicked(view: View, movieId: Int)
    }

    override fun onAdapterItemClicked(view: View, movieInfo: MovieInfo) {
        popularMovieFragmentListener.onMovieClicked(view, movieInfo.movieId)
    }
}