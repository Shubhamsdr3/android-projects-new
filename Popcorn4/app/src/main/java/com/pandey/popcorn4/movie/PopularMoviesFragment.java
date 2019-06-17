package com.pandey.popcorn4.movie;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseFragment;
import com.pandey.popcorn4.base.TitleTextToolbar;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class PopularMoviesFragment extends BaseFragment
        implements PopularMoviePresenter.PopularMovieView, PopularMovieAdapter.AdapterClickCallback {

    private PopularMoviesFragmentListener mListener;

    @BindView(R.id.popular_movie_list)
    RecyclerView recyclerView;

    @BindView(R.id.movie_loading)
    ImageView vMovieLoader;

    public static PopularMoviesFragment newInstance() {
        PopularMoviesFragment fragment = new PopularMoviesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initLayout() {
        vMovieLoader.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        PopularMoviePresenter popularMoviePresenter = new PopularMoviePresenter(this);
        popularMoviePresenter.fetchPopularMovies();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PopularMoviesFragmentListener) {
            mListener = (PopularMoviesFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public FrameLayout getToolBar() {
        return new TitleTextToolbar(Objects.requireNonNull(getActivity()), getString(R.string.popular_movies_title), false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public int getLayoutFile() {
        return R.layout.fragment_popular_movies;
    }

    @Override
    public void onPopularMovieFetching() {
        if(getContext()!= null) {
            Glide
                    .with(getContext())
                    .asGif()
                    .load(R.drawable.movie)
                    .into(vMovieLoader);
        }
    }

    @Override
    public void onPopularMoviesFetched(List<MoviesResponseDto> movieList) {
        vMovieLoader.setVisibility(View.GONE);
        PopularMovieAdapter popularMovieAdapter =
                new PopularMovieAdapter(movieList, getContext(), this);
        if (recyclerView != null) {
            recyclerView.setAdapter(popularMovieAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    @Override
    public void onAdapterItemClick(@NonNull MoviesResponseDto movieDto) {
        mListener.onMovieDetailClicked(movieDto.getId());
    }

    public interface PopularMoviesFragmentListener {
        void onMovieDetailClicked(int movieId);
    }
}
