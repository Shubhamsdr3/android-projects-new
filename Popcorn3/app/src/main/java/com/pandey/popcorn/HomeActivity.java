package com.pandey.popcorn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pandey.popcorn.movies.fragment.PopularMoviesFragment;
import com.pandey.popcorn.news.data.fragment.NewsFragment;

public class HomeActivity extends AppCompatActivity implements
        PopularMoviesFragment.PopularMoviesFragmentListener, NewsFragment.NewsFragmentInteractionListener {

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
        setContentView(R.layout.activity_main);
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

        vPopularMoviesFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.i("Movie fragment is clicked....");
                int currentFragment = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentFragment, false);
            }
        });

        vNewsFeedFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.i("News fragment is clicked....");
                int currentFragment = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentFragment, false);
            }
        });
    }


    @Override
    public void onPopularMovieFragmentListener() {

    }

    @Override
    public void onNewsFragmentInteractionListener() {
    }

    public  static class HomePageAdapter extends FragmentPagerAdapter {

        private static int NUM_ITEMS = 2;

        HomePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new NewsFragment();
                case 1:
                    return new PopularMoviesFragment();
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
