package com.pandey.popcorn4.mediaplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pandey.popcorn4.AppConfig;
import com.pandey.popcorn4.base.BasePresenter;
import com.pandey.popcorn4.data.network.RetrofitHelper;
import com.pandey.popcorn4.mediaplayer.data.VideoDto;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


class MediaPlayerPresenter extends BasePresenter {

    @Nullable
    private MediaPlayerView mMediaPlayerView;

    MediaPlayerPresenter(@Nullable MediaPlayerView mMediaPlayerView) {
        this.mMediaPlayerView = mMediaPlayerView;
    }

    void fetchMovieTrailer(int movieId) {
        String movieTrailerUrl = AppConfig.getMovieBaseUrl() + movieId + "/videos?";
        RetrofitHelper.Companion
                .getApiService()
                .getMovieTrailer(movieTrailerUrl, AppConfig.getEngLang(), AppConfig.getMovieApiKey()
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (mMediaPlayerView != null) {
                        mMediaPlayerView.onVideoFetching();
                    }
                })
                .doOnSuccess(videoResponseDto -> {
                    if (mMediaPlayerView != null) {
                        mMediaPlayerView.onVideoFetchingSuccess(videoResponseDto.getResults());
                    }
                })
                .doOnError(throwable -> {
                    if (mMediaPlayerView != null) {
                        mMediaPlayerView.onVideoFetchingFailed();
                    }
                })
                .subscribe();
    }

    public interface MediaPlayerView {
        void onVideoFetching();
        void onVideoFetchingFailed();
        void onVideoFetchingSuccess(@NonNull List<VideoDto> videoList);
    }
}
