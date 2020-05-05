package com.pandey.popcorn4.moviedetails

import android.net.Uri
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.MediaController
import com.bumptech.glide.Glide
import com.pandey.popcorn4.AppConfig
import com.pandey.popcorn4.R
import com.pandey.popcorn4.base.BaseFragment
import com.pandey.popcorn4.customeviews.KeyValueView
import com.pandey.popcorn4.moviedetails.MovieDetailsActivity.MOVIE_ID
import com.pandey.popcorn4.moviedetails.data.MovieDto
import kotlinx.android.synthetic.main.fragment_movie_details.*
import java.util.*
import kotlin.properties.Delegates

open class MovieDetailFragment  : BaseFragment<MovieDetailFragment.MovieDetailFragmentListener>(), MovieDetailPresenter.MovieDetailView {

    private val MOVIE_ID = "MOVIE_ID"

    private var movieId by Delegates.notNull<Int>()

    companion object {
        fun newInstance(movieId: Int): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            val args = Bundle()
            args.putInt(MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun beforeInit() {
        super.beforeInit()
        movieId = arguments?.getInt(MOVIE_ID)!!
    }

    override fun initLayout() {
        val movieDetailPresenter = MovieDetailPresenter(this)
        movieDetailPresenter.getMovieDetail(movieId)
        activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun initListeners() {
//        vMoviePosterVideo.setOnClickListener(
//                v -> getActivityCommunicator().onMoviePosterClicked()
//        );
    }

    override fun onMovieDetailsFetching() {
        loading_animation.playAnimation()
    }

    override fun onMovieDetailFetchingFailed() {}

    override fun onMovieDetailCompleted(movie: MovieDto) {
        loading_animation.pauseAnimation()
        loading_animation.visibility = View.GONE

//        vMovieTagline.setText(movie.getTagline());
//        vMovieBudget.setText(getString(R.string.movie_budget, String.valueOf(movie.getBudget())));
        movie_title.text = movie.title
        movie_description.text = movie.overview

//        vMovieLanguage.setText(movie.getOriginal_language());
        val movieGenres = StringBuilder()
        for (genres in movie.genres) {
            movieGenres.append(genres.name + ", ")
        }
        val pairList: MutableList<Pair<String?, String?>> = ArrayList()
        Pair(getString(R.string.studio_title), movie.production_companies!![0].name).let { pairList.add(it) }
        pairList.add(Pair(getString(R.string.genre_title), movieGenres.toString()))
        pairList.add(Pair(getString(R.string.release_title), movie.release_date))
        for (pair in pairList) {
            val valueView = KeyValueView(context)
            valueView.setData(pair)
            val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(0, 0, 0, 10)
            key_value_view.addView(valueView)
        }
        val imageBaseUrl = AppConfig.getMovieImageBaseUrl() + movie.posterPath
        //
//        //FIXME:SHUBHAM
//        final Bitmap[] bitmap = {null};
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                bitmap[0] = ImageHelper.getBitmapFromUrl(imageBaseUrl);
//            }
//        }, 500) ;

        Glide.with(context!!)
                .load(imageBaseUrl)
                .thumbnail(0.1f)
//                .placeholder(R.drawable.)
                .into(movie_detail_image)

        var videoId: String? = null
        if (movie.videos != null) {
            for (video in movie.videos!!.results) {
                if (video.type != null && video.type.equals("Trailer", ignoreCase = true)) {
                    videoId = video.key
                }
            }
        }
        val mediaController = MediaController(getContext())
        //        mediaController.setAnchorView(vMoviePosterVideo);
        val uri = Uri.parse(AppConfig.getYoutubeLink() + videoId)
//        vMoviePosterVideo.setMediaController(mediaController);
//        vMoviePosterVideo.setVideoURI(uri);
//        vMoviePosterVideo.requestFocus();
//
//        vMoviePosterVideo.start();


//        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.add(R.id.youtube_player_view, youTubePlayerFragment).commit();
//
//        String finalVideoId = videoId;
//        youTubePlayerFragment.initialize(AppConfig.getGoogleApiKey(), new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                youTubePlayer.loadVideo(finalVideoId);
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//
//            }
//        });
    }

    override fun getToolBar(): FrameLayout? {
        return null
    }

    override fun getListenerClass(): Class<MovieDetailFragmentListener> {
        return MovieDetailFragmentListener::class.java
    }

    override fun getLayoutFile(): Int {
        return R.layout.fragment_movie_details
    }

    interface MovieDetailFragmentListener {
        fun onMoviePosterClicked()
    }
}