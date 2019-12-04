package com.pandey.popcorn4.movie;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.google.firebase.database.ValueEventListener;
import com.pandey.popcorn4.PopApplication;
import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseFragment;
import com.pandey.popcorn4.customeviews.TitleTextToolbar;
import com.pandey.popcorn4.firebase.FirebaseDatabaseUtil;
import com.pandey.popcorn4.fvrtmovies.data.FvrtMovieDbObjectConverter;
import com.pandey.popcorn4.movie.data.MovieInfo;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;
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

    private static final int PICK_CONTACT_REQUEST = 1;

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

//        vFvrtMovieIcon.setOnClickListener(v -> getActivityCommunicator().onFvrtMovieIconClicked(v));

        vFvrtMovieIcon.setOnClickListener(v -> {

           PopApplication.getInstance().getGlobalBuses().send("Hello, you just clicked on Event bus...");

//            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
//                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
//            startActivityForResult(contactPickerIntent , PICK_CONTACT_REQUEST);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setContactValue(data);
    }

    private void setContactValue(@Nullable Intent data) {
        Cursor cursor = null;
        try {
            String name, phoneNo;
            if (data != null) {
                Uri uri = data.getData();
                getContext();
                if (uri != null) {
                    cursor = getContext().getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                        name = cursor.getString(nameIndex);
//                        if (!TextUtils.isEmpty(name)) {
////                            vDriverNameEt.setText(name);
//                        }
                        int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        phoneNo = cursor.getString(phoneIndex);
                        if (!TextUtils.isEmpty(phoneNo)) {
                            phoneNo = phoneNo.replace("+91", "");
                            phoneNo = phoneNo.replaceAll("[^\\d]", "");
                            if (phoneNo.length() > 10) {
                                phoneNo = phoneNo.substring(phoneNo.length() - 10);
                            }
//                            vDriverPhoneEt.setText(phoneNo);

                            Toast.makeText(getContext(), name + " - " +  phoneNo, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

//            startDialog(UpdateDriverDetailsDialog.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
//        workManager.getWorkInfoByIdLiveData(mRequest.getId()).observe(this, workInfo -> {
//            if (workInfo != null) {
//                WorkInfo.State state = workInfo.getState();
//                Toast.makeText(getActivity(), "" + state.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
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
                mPopularMoviePresenter.saveToDb(FvrtMovieDbObjectConverter.toDbObject(movieInfo));
            }
        }
    }

    public interface PopularMoviesFragmentListener {

        void onMovieDetailClicked(@NonNull View view,  int movieId);

        void onSearchIconClicked();

        void onFvrtMovieIconClicked(@NonNull View view);
    }
}
