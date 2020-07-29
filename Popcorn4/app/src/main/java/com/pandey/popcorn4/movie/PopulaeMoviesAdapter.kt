package com.pandey.popcorn4.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pandey.popcorn4.R
import com.pandey.popcorn4.movie.data.MovieInfo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.movie_card_view.view.*

class PopularMoviesAdapter(movieList: List<MovieInfo>, callback: PopularMovieAdapterCallback) : RecyclerView.Adapter<PopularMoviesAdapter.PopularMovieViewHolder>() {

    private val movieInfoList : List<MovieInfo> = movieList

    private val popularMovieAdapterCallback : PopularMovieAdapterCallback = callback

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.movie_card_view, parent,false)
        return PopularMovieViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return movieInfoList.size
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        holder.bind(movieInfoList[position])
    }

    inner class PopularMovieViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView) , LayoutContainer {

        fun bind(movieInfo: MovieInfo) {
            val imageBaseUrl = IMAGE_BASE_URL + movieInfo.moviePoster
            Glide.with(containerView.context)
                    .load(imageBaseUrl)
                    .into(containerView.movie_poster_image)
            containerView.movie_title.text = movieInfo.movieTitle
            containerView.movie_stars_view.showStars(movieInfo.movieStar)

            containerView.setOnClickListener {
                popularMovieAdapterCallback.onAdapterItemClicked(it, movieInfo)
            }
        }
    }

    interface PopularMovieAdapterCallback {
        fun onAdapterItemClicked(view: View, movieInfo: MovieInfo)
    }
}