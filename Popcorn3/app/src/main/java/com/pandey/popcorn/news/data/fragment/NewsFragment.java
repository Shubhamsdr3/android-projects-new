package com.pandey.popcorn.news.data.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.pandey.popcorn.R;
import com.pandey.popcorn.news.data.Article;
import com.pandey.popcorn.news.data.adapter.NewsAdapter;
import com.pandey.popcorn.news.data.presenter.NewsPresenter;

import java.util.List;

public class NewsFragment extends Fragment implements NewsPresenter.NewsView {

    @Nullable
    private NewsFragmentInteractionListener mListener;

    @Nullable
    private NewsPresenter newsPresenter;

    @BindView(R.id.news_feed_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.news_load_status_bar)
    ProgressBar vNewsProgressBar;

    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
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
        recyclerView.setHasFixedSize(true);
        newsPresenter = new NewsPresenter(this);
        newsPresenter.getNewsHeadlines();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NewsFragmentInteractionListener) {
            mListener = (NewsFragmentInteractionListener) context;
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
    public void onNewsFeedFetchingStarted() {
        vNewsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessfullyFetchedArticle(List<Article> articles) {
        vNewsProgressBar.setVisibility(View.GONE);
        NewsAdapter newsAdapter  = new NewsAdapter(articles, getContext());
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public interface NewsFragmentInteractionListener {
        void onNewsFragmentInteractionListener();
    }
}
