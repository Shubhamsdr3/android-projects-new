package com.pandey.popcorn4.fvrtmovies;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseFragment;
import com.pandey.popcorn4.movie.data.MovieInfo;
import com.pandey.popcorn4.movie.PopularMovieAdapter;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class FvrtMovieFragment
        extends BaseFragment<FvrtMovieFragment.FvrtMovieFragmentListener>
        implements FvrtMoviePresenter.FvrtMovieView, PopularMovieAdapter.AdapterClickCallback {

    static FvrtMovieFragment newInstance() {
        FvrtMovieFragment fvrtMovieFragment = new FvrtMovieFragment();
        Bundle bundle = new Bundle();
        fvrtMovieFragment.setArguments(bundle);
        return fvrtMovieFragment;
    }

    @BindView(R.id.fvrt_movie_list)
    RecyclerView vFvrtMovieListView;

    @Override
    public void initLayout() {
        FvrtMoviePresenter fvrtMoviePresenter =
                new FvrtMoviePresenter(Objects.requireNonNull(getContext()), this);
        fvrtMoviePresenter.fetchFromDb();
    }

    @Override
    public void initListeners() {

    }

    @Nullable
    @Override
    public FrameLayout getToolBar() {
        return null;
    }

    @NonNull
    @Override
    protected Class<FvrtMovieFragmentListener> getListenerClass() {
        return FvrtMovieFragmentListener.class;
    }

    @Override
    public int getLayoutFile() {
        return R.layout.fragment_fvrt_movies;
    }

    @Override
    public void onDbFetchSuccess(@NonNull List<MovieInfo> movieInfoList) {
        if (movieInfoList.size() > 0) {
            PopularMovieAdapter popularMovieAdapter =
                    new PopularMovieAdapter(
                            movieInfoList,
                            Objects.requireNonNull(getContext()),
                            this
                    );
            vFvrtMovieListView.setAdapter(popularMovieAdapter);
            vFvrtMovieListView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            // show the no movie container.
        }

    }

    @Override
    public void onAdapterItemClick(@NonNull MovieInfo movieInfo) {

    }

    public interface FvrtMovieFragmentListener {

    }
}
