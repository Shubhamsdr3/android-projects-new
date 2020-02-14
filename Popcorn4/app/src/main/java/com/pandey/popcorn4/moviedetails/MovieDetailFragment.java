package com.pandey.popcorn4.moviedetails;

import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.pandey.popcorn4.AppConfig;
import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseFragment;
import com.pandey.popcorn4.customeviews.KeyValueView;
import com.pandey.popcorn4.mediaplayer.data.VideoDto;
import com.pandey.popcorn4.movie.customviews.MovieStarsView;
import com.pandey.popcorn4.moviedetails.data.MovieDto;
import com.pandey.popcorn4.moviedetails.data.MovieGenresDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class MovieDetailFragment extends
        BaseFragment<MovieDetailFragment.MovieDetailFragmentListener>
        implements MovieDetailPresenter.MovieDetailView {

    private static final String MOVIE_ID = "MOVIE_ID";

    private int movieId;

//    @BindView(R.id.movie_poster)
//    VideoView vMoviePosterVideo;

    @BindView(R.id.movie_poster_image)
    ImageView vMoviePosterImage;

    @BindView(R.id.movie_title)
    TextView vMovieTitleView;

//    @BindView(R.id.movie_tagline)
//    TextView vMovieTagline;

    @BindView(R.id.watch_now_button)
    Button vWatchNowButton;

    @BindView(R.id.movie_stars_view)
    MovieStarsView vMovieStarsView;

    @BindView(R.id.movie_description)
    TextView vMovieDescription;

    @BindView(R.id.movie_cast_list)
    RecyclerView vMovieCastList;

    @BindView(R.id.key_value_view)
    ViewGroup vMovieNameValueContainer;

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

        getActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    @Override
    public void initListeners() {
//        vMoviePosterVideo.setOnClickListener(
//                v -> getActivityCommunicator().onMoviePosterClicked()
//        );
    }

    @Override
    public void onMovieDetailsFetching() {
        vLoadingAnimation.playAnimation();
    }

    @Override
    public void onMovieDetailCompleted(MovieDto movie) {
        vLoadingAnimation.pauseAnimation();
        vLoadingAnimation.setVisibility(View.GONE);

//        vMovieTagline.setText(movie.getTagline());
//        vMovieBudget.setText(getString(R.string.movie_budget, String.valueOf(movie.getBudget())));

        vMovieTitleView.setText(movie.getTitle());
        vMovieDescription.setText(movie.getOverview());

//        vMovieLanguage.setText(movie.getOriginal_language());

        StringBuilder movieGenres = new StringBuilder();
        for (MovieGenresDto genres: movie.getGenres()) {
            movieGenres.append(genres.getName() + ", ");
        }

        List<Pair<String, String>> pairList = new ArrayList<>();
        pairList.add(new Pair<>(getString(R.string.studio_title), movie.getProduction_companies().get(0).getName()));
        pairList.add(new Pair<>(getString(R.string.genre_title), movieGenres.toString()));
        pairList.add(new Pair<>(getString(R.string.release_title), movie.getRelease_date()));

        for (Pair<String, String> pair: pairList) {
            KeyValueView valueView = new KeyValueView(getContext());
            valueView.setData(pair);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 10);
            vMovieNameValueContainer.addView(valueView);
        }

        String imageBaseUrl = AppConfig.getMovieImageBaseUrl() + movie.getPosterPath();
//
//        //FIXME:SHUBHAM
//        final Bitmap[] bitmap = {null};
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                bitmap[0] = ImageHelper.getBitmapFromUrl(imageBaseUrl);
//            }
//        }, 500) ;

        Glide.with(Objects.requireNonNull(getContext()))
                .load(imageBaseUrl)
                .thumbnail(0.1f)
//                .placeholder(R.drawable.)
                .into(vMoviePosterImage);

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
//        mediaController.setAnchorView(vMoviePosterVideo);
        Uri uri = Uri.parse(AppConfig.getYoutubeLink() + videoId);
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
        return R.layout.fragment_movie_details;
    }

    interface MovieDetailFragmentListener {
        void onMoviePosterClicked();
    }
}
