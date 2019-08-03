package com.pandey.popcorn4.movie;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseFragment;
import com.pandey.popcorn4.customeviews.TitleTextToolbar;
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

    @Nullable
    private PopularMoviesFragmentListener mListener;

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mRecentMovieSearch = mRootRef.child("movie1");

    @BindView(R.id.popular_movie_list)
    RecyclerView recyclerView;

    @BindView(R.id.movie_loading)
    ImageView vMovieLoader;

    @BindView(R.id.search_icon)
    ImageView vSearchIcon;

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
        vSearchIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void initListeners() {
        mRecentMovieSearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                Toast.makeText(getContext(), "This is the text..." + text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        vSearchIcon.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onSearchIconClicked();
            }
        });
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
        TitleTextToolbar toolbar =  new TitleTextToolbar(Objects.requireNonNull(getActivity()), getString(R.string.popular_movies_title),  false);
        toolbar.setRightView(vSearchIcon);
        return toolbar;
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
        if (mListener != null) {
            mListener.onMovieDetailClicked(movieDto.getId());
        }
    }

    public interface PopularMoviesFragmentListener {
        void onMovieDetailClicked(int movieId);
        void onSearchIconClicked();
    }
}
