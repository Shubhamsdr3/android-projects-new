package com.pandey.popcorn.movies.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pandey.popcorn.R;
import com.pandey.popcorn.movies.fragment.adapter.PopularMovieAdapter;
import com.pandey.popcorn.movies.fragment.data.Movie;
import com.pandey.popcorn.movies.fragment.presenter.PopularMoviePresenter;

import java.util.List;

public class PopularMoviesFragment extends Fragment  implements PopularMoviePresenter.PopularMovieView {

    private PopularMoviesFragmentListener mListener;

    @NonNull
    PopularMoviePresenter popularMoviePresenter;

    @NonNull
    private RecyclerView recyclerView;

    public static PopularMoviesFragment newInstance() {
        PopularMoviesFragment fragment = new PopularMoviesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_popular_movies, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.popular_movie_list);
        recyclerView.setHasFixedSize(true);
        popularMoviePresenter = new PopularMoviePresenter(this);
        popularMoviePresenter.fetchPopularMovies();
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPopularMoviesFetched(List<Movie> movieList) {
        PopularMovieAdapter popularMovieAdapter =
                new PopularMovieAdapter(movieList, getContext());
        recyclerView.setAdapter(popularMovieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public interface PopularMoviesFragmentListener {
        void onPopularMovieFragmentListener();
    }
}
