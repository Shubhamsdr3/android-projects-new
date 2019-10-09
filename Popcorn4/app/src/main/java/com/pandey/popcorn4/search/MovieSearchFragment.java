package com.pandey.popcorn4.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pandey.popcorn4.R;
import com.pandey.popcorn4.base.BaseFragment;
import com.pandey.popcorn4.customeviews.KeyboardUtils;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;
import com.pandey.popcorn4.movie.data.RecentMovieSearched;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class MovieSearchFragment extends BaseFragment implements MovieSearchAdapterFilterable.MovieSearchAdapterListener,
        MovieSearchPresenter.MovieSearchView {

    @Nullable
    private CompositeDisposable disposable = new CompositeDisposable();

    HashMap<String, RecentMovieSearched> recentSearches = new HashMap<>();

    private SharedPreferences sharedpreferences;

    private int itemCount;

    private static final String RECENT_MOVIE_SEARCH = "RECENT_MOVIE_SEARCH";

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Nullable
    private MovieSearchPresenter movieSearchPresenter;

    @NonNull
    private List<MoviesResponseDto> moviesList = new ArrayList<>();

    @Nullable
    private MovieSearchFragmentInteractionListener mListener;

    @BindView(R.id.search_icon)
    ImageView vSearchIcon;

    @BindView(R.id.nav_back)
    TextView vNavigationBack;

    @BindView(R.id.no_result_found)
    TextView vNoResultFound;

    @BindView(R.id.searched_movie_list)
    RecyclerView recyclerView;

    @BindView(R.id.search_edit_text)
    EditText vSearchInputEt;

    public static MovieSearchFragment newInstance() {
        MovieSearchFragment fragment = new MovieSearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initLayout() {
        movieSearchPresenter = new MovieSearchPresenter(this);

        if(getActivity() != null) {
            sharedpreferences = getActivity().getSharedPreferences(RECENT_MOVIE_SEARCH, Context.MODE_PRIVATE);
        }
    }

    @Override
    public void initListeners() {
        vSearchInputEt.requestFocus();
        KeyboardUtils.showKeyboard(vSearchInputEt.getContext(), vSearchInputEt);
        vSearchInputEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (movieSearchPresenter != null) {
                    movieSearchPresenter.fetchSearchedMovie(s.toString());
                }
            }
        });

        vNavigationBack.setOnClickListener(v -> {
            if(getActivity() != null) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void afterInit() {
        super.afterInit();
    }

    @Nullable
    @Override
    public FrameLayout getToolBar() {
        return null;
    }

    @Override
    public int getLayoutFile() {
        return R.layout.fragment_movie_search;
    }

    @Override
    public void onMovieSelected(@Nullable MoviesResponseDto moviesResponseDto) {
        //TODO replace it with user id.
        itemCount ++;
        // Saving to fire base
        RecentMovieSearched recentMovieSearched =
                new RecentMovieSearched(moviesResponseDto.getId(),
                        moviesResponseDto.getPopularity(),
                        moviesResponseDto.isAdult(),
                        moviesResponseDto.getPoster_path(),
                        moviesResponseDto.getTitle(),
                        moviesResponseDto.getOriginal_language(),
                        moviesResponseDto.getOverview(),
                        moviesResponseDto.getRelease_date());
        recentSearches.put(String.valueOf(itemCount), recentMovieSearched);
        Timber.i("Saving searched to db..%s", recentSearches);
        mRootRef.child("recentSearch").setValue(recentSearches);
        if (mListener != null) {
            mListener.onMovieSelectedFromSearch(Objects.requireNonNull(moviesResponseDto));
        }
    }

    @Override
    public void onMovieFetching() {
    }

    //TODO: move this to base fragment..
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof MovieSearchFragmentInteractionListener) {
            mListener = (MovieSearchFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    protected Class getListenerClass() {
        return MovieSearchFragmentInteractionListener.class;
    }

    @Override
    public void onMovieFetchedSuccess(@NonNull List<MoviesResponseDto> movie) {
        vNoResultFound.setVisibility(View.GONE);
        KeyboardUtils.hideKeyword(vSearchInputEt.getContext(), vSearchInputEt);
        this.moviesList.addAll(movie);

        MovieSearchAdapterFilterable movieSearchAdapterFilterable =
                new MovieSearchAdapterFilterable(getContext(), moviesList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(movieSearchAdapterFilterable);
        movieSearchAdapterFilterable.notifyDataSetChanged();
    }

    @Override
    public void onMovieFetchedFailed() {
        vNoResultFound.setVisibility(View.VISIBLE);
        KeyboardUtils.hideKeyword(vSearchInputEt.getContext(), vSearchInputEt);
    }

    public interface MovieSearchFragmentInteractionListener {
        void onMovieSelectedFromSearch(@Nullable MoviesResponseDto moviesResponseDto);
    }
}
