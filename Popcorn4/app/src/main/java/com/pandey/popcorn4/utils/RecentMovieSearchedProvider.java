//package com.pandey.popcorn4.utils;
//
//import android.content.Context;
//import android.content.SearchRecentSuggestionsProvider;
//import android.content.SharedPreferences;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//public class RecentMovieSearchedProvider extends SearchRecentSuggestionsProvider {
//
//    @NonNull
//    private Context context;
//
//    private SharedPreferences mSharedPreferneces;
//
//    private static final String RECENT_MOVIE_SEARCHES  = "RECENT_MOVIE_SEARCHES";
//
//    RecentMovieSearchedProvider() {
//
//    }
//
//    public RecentMovieSearchedProvider(@NonNull Context context) {
//        this.context = context;
//        mSharedPreferneces = context.getSharedPreferences(RECENT_MOVIE_SEARCHES, Context.MODE_PRIVATE);
//    }
//
//    public void getRecentSearchedMovies(@Nullable View view) {
//
//    }
//
//}
