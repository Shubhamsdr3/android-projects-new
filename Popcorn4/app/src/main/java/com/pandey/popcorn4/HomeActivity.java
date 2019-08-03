package com.pandey.popcorn4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.pandey.popcorn4.base.BaseActivity;
import com.pandey.popcorn4.movie.PopularMoviesFragment;
import com.pandey.popcorn4.movie.data.MoviesResponseDto;
import com.pandey.popcorn4.moviedetails.MovieDetailsActivity;
import com.pandey.popcorn4.news.NewsFragment;
import com.pandey.popcorn4.search.MovieSearchFragment;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeActivity extends BaseActivity implements
        PopularMoviesFragment.PopularMoviesFragmentListener, NewsFragment.NewsFragmentInteractionListener ,
        MovieSearchFragment.MovieSearchFragmentInteractionListener {

    private static final String MOVIE_ID = "MOVIE_ID";

    @BindView(R.id.popular_movies_text)
    TextView vPopularMoviesFragment;

    @BindView(R.id.news_feed)
    TextView vNewsFeedFragment;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        adapterViewPager = new HomePageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vPopularMoviesFragment.setOnClickListener(v -> {
            int currentFragment = viewPager.getCurrentItem();
            viewPager.setCurrentItem(currentFragment, false);
        });

        vNewsFeedFragment.setOnClickListener(v -> {
            int currentFragment = viewPager.getCurrentItem();
            viewPager.setCurrentItem(currentFragment, false);
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onMovieDetailClicked(int movieId) {
        Intent newActivity =  new Intent(this, MovieDetailsActivity.class);
        newActivity.putExtra(MOVIE_ID, movieId);
        startActivity(newActivity);
    }

    @Override
    public void onSearchIconClicked() {
        startFragment(MovieSearchFragment.newInstance(), true);
    }

    @Override
    public void onNewsFragmentInteractionListener() {
    }

    @Override
    public void onMovieSelectedFromSearch(@Nullable MoviesResponseDto moviesResponseDto) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MOVIE_ID, moviesResponseDto.getId());
        startActivity(intent);
    }

    public  static class HomePageAdapter extends FragmentPagerAdapter {

        private static int NUM_ITEMS = 1;
        HomePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return PopularMoviesFragment.newInstance();
//                case 1:
//                    return new NewsFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }
}
