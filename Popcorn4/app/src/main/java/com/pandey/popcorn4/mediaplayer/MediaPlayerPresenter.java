package com.pandey.popcorn4.mediaplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pandey.popcorn4.AppConfig;
import com.pandey.popcorn4.base.BasePresenter;
import com.pandey.popcorn4.mediaplayer.data.VideoDto;
import com.pandey.popcorn4.mediaplayer.data.VideoResponseDto;
import com.pandey.popcorn4.utils.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class MediaPlayerPresenter extends BasePresenter {

    @NonNull
    private List<VideoDto> mVideoList = new ArrayList<>();

    @Nullable
    private MediaPlayerView mMediaPlayerView;


    MediaPlayerPresenter(@Nullable MediaPlayerView mMediaPlayerView) {
        this.mMediaPlayerView = mMediaPlayerView;
    }

    void fetchMovieTrailer(int movieId) {
        String movieTrailerUrl = AppConfig.getMovieBaseUrl() + movieId + "/videos?";
        RetrofitHelper
                .getApiService()
                .getMovieTrailer(
                        movieTrailerUrl,
                        AppConfig.getEngLang(),
                        AppConfig.getMovieApiKey()
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoResponseDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VideoResponseDto videoResponseDto) {
                        mVideoList.addAll(videoResponseDto.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.i(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        if (mMediaPlayerView != null) {
                            mMediaPlayerView.onVideoFetchSuccess(mVideoList);
                        }
                    }
                });

    }

    public interface MediaPlayerView {
        void onVideoFetchSuccess(@NonNull List<VideoDto> videoList);
    }
}
