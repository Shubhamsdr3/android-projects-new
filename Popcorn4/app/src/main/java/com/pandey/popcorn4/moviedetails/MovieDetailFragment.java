package com.pandey.popcorn4.moviedetails;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.pandey.popcorn4.AppConfig;
import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseFragment;
import com.pandey.popcorn4.mediaplayer.data.VideoDto;
import com.pandey.popcorn4.moviedetails.data.MovieGenresDto;
import com.pandey.popcorn4.moviedetails.data.MovieDto;

import java.util.HashSet;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;

public class MovieDetailFragment extends
        BaseFragment<MovieDetailFragment.MovieDetailFragmentListener>
        implements MovieDetailPresenter.MovieDetailView {

    private static final String MOVIE_ID = "MOVIE_ID";

    private int movieId;

    @BindView(R.id.movie_poster)
    VideoView vMoviePosterVideo;

    @BindView(R.id.movie_title_text)
    TextView vMovieTitleView;

    @BindView(R.id.movie_tagline)
    TextView vMovieTagline;

    @BindView(R.id.movie_language)
    TextView vMovieLanguage;

    @BindView(R.id.movie_genres)
    TextView vMovieGenres;

    @BindView(R.id.movie_budget)
    TextView vMovieBudget;

    @BindView(R.id.release_date)
    TextView vReleaseDate;

    @BindView(R.id.loading_animation)
    LottieAnimationView vLoadingAnimation;

    static MovieDetailFragment newInstance(int movieId) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putInt(MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void beforeInit() {
        super.beforeInit();
        if(getArguments() != null) {
            movieId = getArguments().getInt(MOVIE_ID);
        }
    }

    @Override
    public void initLayout() {
        MovieDetailPresenter movieDetailPresenter = new MovieDetailPresenter(this);
        movieDetailPresenter.getMovieDetail(movieId);
    }

    @Override
    public void initListeners() {
        vMoviePosterVideo.setOnClickListener(
                v -> getActivityCommunicator().onMoviePosterClicked()
        );
    }

    @Override
    public void onMovieDetailsFetching() {
        vLoadingAnimation.playAnimation();
    }

    @Override
    public void onMovieDetailCompleted(MovieDto movie) {
        vLoadingAnimation.pauseAnimation();
        vLoadingAnimation.setVisibility(View.GONE);
//        String imageBaseUrl = AppConfig.getMovieImageBaseUrl() +  movie.getPosterPath();
//        Glide.with(Objects.requireNonNull(getContext()))
//                .load(imageBaseUrl)
////                .placeholder(R.drawable.)
//                .into(vMoviePosterImage);

        String videoId = "";
        if (movie.getVideos() != null) {
            for (VideoDto video : movie.getVideos().getResults()) {
                if (video.getType() != null
                        && video.getType().equalsIgnoreCase("Trailer")) {
                    videoId = video.getKey();
                }
            }
        }

        MediaController mediaController= new MediaController(getContext());
        mediaController.setAnchorView(vMoviePosterVideo);
        Uri uri = Uri.parse(AppConfig.getYoutubeLink() + videoId);
        vMoviePosterVideo.setMediaController(mediaController);
        vMoviePosterVideo.setVideoURI(uri);
        vMoviePosterVideo.requestFocus();

        vMoviePosterVideo.start();


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

        vMovieTitleView.setText(movie.getTitle());
        vMovieTagline.setText(movie.getTagline());
        vMovieBudget.setText(getString(R.string.movie_budget, String.valueOf(movie.getBudget())));
        vMovieLanguage.setText(movie.getOriginal_language());
        for (MovieGenresDto genres: movie.getGenres()) {
            vMovieGenres.setText(genres.getName());
        }
        vReleaseDate.setText(movie.getRelease_date());
    }

    @Nullable
    @Override
    public FrameLayout getToolBar() {
        return null;
    }

    @NonNull
    @Override
    protected Class<MovieDetailFragmentListener> getListenerClass() {
        return MovieDetailFragmentListener.class;
    }

    @Override
    public int getLayoutFile() {
        return R.layout.layout_movie;
    }

    interface MovieDetailFragmentListener {
        void onMoviePosterClicked();
    }
}
