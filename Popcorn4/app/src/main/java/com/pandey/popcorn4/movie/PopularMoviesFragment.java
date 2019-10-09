package com.pandey.popcorn4.movie;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseFragment;
import com.pandey.popcorn4.customeviews.TitleTextToolbar;
import com.pandey.popcorn4.db.AppDatabase;
import com.pandey.popcorn4.firebase.FirebaseDatabaseUtil;
import com.pandey.popcorn4.fvrtmovies.data.FvrtMovieDbObjectConverter;
import com.pandey.popcorn4.movie.data.MovieInfo;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class PopularMoviesFragment
        extends BaseFragment<PopularMoviesFragment.PopularMoviesFragmentListener>
        implements PopularMoviePresenter.PopularMovieView,
        PopularMovieAdapter.AdapterClickCallback, SwipeToDismissCallback.SwipeToDismissListener {

    @BindView(R.id.popular_movie_list)
    RecyclerView recyclerView;

//    @BindView(R.id.search_icon)
//    ImageView vSearchIcon;

    @BindView(R.id.liked_movies_icon)
    ImageView vFvrtMovieIcon;

    @BindView(R.id.loading_animation)
    LottieAnimationView vLoadingView;

    @Nullable
    private PopularMoviePresenter mPopularMoviePresenter;

    public static PopularMoviesFragment newInstance() {
        PopularMoviesFragment fragment = new PopularMoviesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initLayout() {
        recyclerView.setHasFixedSize(true);
        mPopularMoviePresenter = new PopularMoviePresenter(Objects.requireNonNull(getContext()), this);
        mPopularMoviePresenter.fetchPopularMovies();
//        vSearchIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void initListeners() {
        DatabaseReference mRecentMovieSearch = FirebaseDatabaseUtil.getDatabaseReference();
        mRecentMovieSearch.child("movie2").setValue("Endgame");
        mRecentMovieSearch.child("recentSearch");

        mRecentMovieSearch.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        vFvrtMovieIcon.setOnClickListener(v -> {
            getActivityCommunicator().onFvrtMovieIconClicked();
        });
    }

    @NonNull
    @Override
    protected Class<PopularMoviesFragmentListener> getListenerClass() {
        return PopularMoviesFragmentListener.class;
    }

    @Nullable
    @Override
    public FrameLayout getToolBar() {
        TitleTextToolbar toolbar =
                new TitleTextToolbar(
                        Objects.requireNonNull(getActivity()),
                        getString(R.string.popular_movies_title),
                        false
                );
//        toolbar.setRightView(vSearchIcon);
        toolbar.setRightView(vFvrtMovieIcon);
        return toolbar;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public int getLayoutFile() {
        return R.layout.fragment_popular_movies;
    }

    @Override
    public void onPopularMovieFetching() {
        vLoadingView.playAnimation();
//        if(getContext()!= null) {
//            Glide
//                    .with(getContext())
//                    .asGif()
//                    .load(R.drawable.movie)
//                    .into(vMovieLoader);
//        }
    }

    @Override
    public void onPopularMoviesFetched(List<MovieInfo> movieList) {
        vLoadingView.setVisibility(View.GONE);
//        vMovieLoader.setVisibility(View.GONE);
        PopularMovieAdapter mPopularMovieAdapter =
                new PopularMovieAdapter(movieList, Objects.requireNonNull(getContext()), this);
        recyclerView.setAdapter(mPopularMovieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SwipeToDismissCallback swipeToDismissCallback =
                new SwipeToDismissCallback(Objects.requireNonNull(getActivity()), this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDismissCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onAdapterItemClick(@NonNull MovieInfo movieInfo) {
        getActivityCommunicator().onMovieDetailClicked(movieInfo.getMovieId());
    }

    @Override
    public void onSwipedItem(int position) {
        PopularMovieAdapter popularMovieAdapter = (PopularMovieAdapter) recyclerView.getAdapter();
        if (popularMovieAdapter != null) {
            MovieInfo movieInfo = popularMovieAdapter.getItem(position);
            if (mPopularMoviePresenter != null) {
                mPopularMoviePresenter.saveToDb(FvrtMovieDbObjectConverter.toDbObject(movieInfo));
            }
        }
    }

    public interface PopularMoviesFragmentListener {

        void onMovieDetailClicked(int movieId);

        void onSearchIconClicked();

        void onFvrtMovieIconClicked();
    }
}
