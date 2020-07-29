package com.pandey.popcorn4.movie;

import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pandey.popcorn4.PopApplication;
import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseFragment;
import com.pandey.popcorn4.data.network.db.FvrtMovieDbObjectConverter;
import com.pandey.popcorn4.movie.customviews.MovieSearchBar;
import com.pandey.popcorn4.movie.data.MovieInfo;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;
import com.pandey.popcorn4.utils.UserContactUtils;
import com.pandey.popcorn4.worker.NotifyUserWorker;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class PopularMoviesFragment
        extends BaseFragment<PopularMoviesFragment.PopularMoviesFragmentListener>
        implements PopularMoviePresenter.PopularMovieView,
        PopularMovieAdapter.AdapterClickCallback, SwipeToDismissCallback.SwipeToDismissListener {

    public static final String MOVIE_TITLE = "MOVIE_TITLE";
    public static final String MOVIE_OVERVIEW = "MOVIE_OVERVIEW";

    @BindView(R.id.popular_movie_list)
    RecyclerView recyclerView;

    @BindView(R.id.movie_search_bar)
    MovieSearchBar vMovieSearchBar;

    @BindView(R.id.movie_list_card)
    CardView vMovieListCard;

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
//        recyclerView.setHasFixedSize(true);
//        mPopularMoviePresenter = new PopularMoviePresenter(Objects.requireNonNull(getContext()), this);
//        mPopularMoviePresenter.fetchPopularMovies();
//        vMovieSearchBar.setVisibility(View.VISIBLE);

        // Curved from top
        int curveRadius = 36;
        vMovieListCard.setOutlineProvider(new ViewOutlineProvider() {

            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), (view.getHeight() + curveRadius),curveRadius);
            }
        });
        vMovieListCard.setClipToOutline(true);
    }

    @Override
    public void initListeners() {
        DatabaseReference mRecentMovieSearch = FirebaseDatabase.getInstance().getReference();
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

//        vFvrtMovieIcon.setOnClickListener(v -> getActivityCommunicator().onFvrtMovieIconClicked(v));

//        vFvrtMovieIcon.setOnClickListener(v -> {
//
//           PopApplication.getInstance().getGlobalBuses().send("Hello, you just clicked on Event bus...");

//            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
//                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
//            startActivityForResult(contactPickerIntent , PICK_CONTACT_REQUEST);
//        });

//        vMovieSearchBar.getEditText().setOnClickListener(
//                v -> getActivityCommunicator().onSearchBarClicked()
//        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Pair<String, String> contact = UserContactUtils.getContactValue(Objects.requireNonNull(getContext()), data);
        Toast.makeText(getContext(), contact.first + " - " + contact.second, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    protected Class<PopularMoviesFragmentListener> getListenerClass() {
        return PopularMoviesFragmentListener.class;
    }

    @Nullable
    @Override
    public FrameLayout getToolBar() {
//        TitleTextToolbar toolbar =
//                new TitleTextToolbar(
//                        Objects.requireNonNull(getActivity()),
//                        getString(R.string.popular_movies_title),
//                        false
//                );
//        toolbar.setRightView(vSearchIcon);
//        toolbar.setRightView(vFvrtMovieIcon);
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPopularMovieFetching() {
//        vLoadingView.playAnimation();
//        if(getContext()!= null) {
//            Glide
//                    .with(getContext())
//                    .asGif()
//                    .load(R.drawable.movie)
//                    .into(vMovieLoader);
//        }
    }

    @Override
    public void onPopularMovieFetchingFailed() {

    }

    @Override
    public void onPopularMoviesFetchingSuccess(List<MovieInfo> movieList) {
        vLoadingView.setVisibility(View.GONE);
//        vMovieLoader.setVisibility(View.GONE);

        PopularMovieAdapter mPopularMovieAdapter =
                new PopularMovieAdapter(movieList, Objects.requireNonNull(getContext()), this);

        recyclerView.setAdapter(mPopularMovieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        SwipeToDismissCallback swipeToDismissCallback =
                new SwipeToDismissCallback(Objects.requireNonNull(getActivity()), this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDismissCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onLatestMovieFetchSuccess(@NonNull MoviesResponseDto moviesResponseDto) {
        WorkManager workManager = WorkManager.getInstance(getContext());
        Data.Builder data = new Data.Builder();
        data.putString(MOVIE_TITLE, moviesResponseDto.getTitle());
        data.putString(MOVIE_OVERVIEW, moviesResponseDto.getOverview());

        // Adding constraint
        Constraints constraints =
                new  Constraints.Builder()
                        .setRequiresBatteryNotLow(true)
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .setRequiresStorageNotLow(true)
                        .build();

        PeriodicWorkRequest mRequest =
                new PeriodicWorkRequest.Builder(
                        NotifyUserWorker.class, 5, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .setInputData(data.build())
                        .build();

        // Enqueue the task request.
        workManager.enqueue(mRequest);
    }

    @Override
    public void onAdapterItemClick(@NonNull View view, @NonNull MovieInfo movieInfo) {
        Bundle bundle = new Bundle();
        bundle.putString("action", "Popular movie clicked");
        bundle.putString("label", "Popular movie clicked");
        PopApplication.getFirebaseAnalytics().logEvent("PopularMovie",bundle);
        getActivityCommunicator().onMovieDetailClicked(view, movieInfo.getMovieId());
    }

    @Override
    public void onSwipedItem(int position) {
        PopularMovieAdapter popularMovieAdapter = (PopularMovieAdapter) recyclerView.getAdapter();
        if (popularMovieAdapter != null) {
            MovieInfo movieInfo = popularMovieAdapter.getItem(position);
            if (mPopularMoviePresenter != null) {
                mPopularMoviePresenter.saveToDb(Objects.requireNonNull(FvrtMovieDbObjectConverter.Companion.toDbObject(movieInfo)));
            }
        }
    }

    @Override
    public int getLayoutFile() {
        return R.layout.fragment_popular_movies;
    }

    public interface PopularMoviesFragmentListener {

        void onMovieDetailClicked(@NonNull View view,  int movieId);

        void onSearchBarClicked();

        void onFvrtMovieIconClicked(@NonNull View view);
    }
}
