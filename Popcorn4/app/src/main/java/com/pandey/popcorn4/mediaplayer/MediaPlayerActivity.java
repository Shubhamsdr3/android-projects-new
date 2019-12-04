package com.pandey.popcorn4.mediaplayer;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.pandey.popcorn4.AppConfig;
import com.pandey.popcorn4.R;
import com.pandey.popcorn4.mediaplayer.data.VideoDto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pandey.popcorn4.moviedetails.MovieDetailsActivity.MOVIE_ID;

public class MediaPlayerActivity extends YouTubeBaseActivity implements MediaPlayerPresenter.MediaPlayerView {

    @BindView(R.id.youtube_player_view)
    YouTubePlayerView vYoutubePlayerView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_media_player);
        ButterKnife.bind(this);

        int movieId = getIntent().getIntExtra(MOVIE_ID, 0);
        MediaPlayerPresenter mediaPlayerPresenter = new MediaPlayerPresenter(this);
        mediaPlayerPresenter.fetchMovieTrailer(movieId);
    }

    @Override
    public void onVideoFetchSuccess(@NonNull List<VideoDto> videoList) {
        String videoId = "";
        for (VideoDto video : videoList) {
            if (video.getType() != null
                    && video.getType().equalsIgnoreCase("Trailer")) {
                videoId = video.getKey();
            }
        }

        String youtubeVideoKey = videoId;
        vYoutubePlayerView.initialize(AppConfig.getGoogleApiKey(), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(youtubeVideoKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}
