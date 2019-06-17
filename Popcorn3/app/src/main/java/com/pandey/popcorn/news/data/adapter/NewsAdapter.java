package com.pandey.popcorn.news.data.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pandey.popcorn.R;
import com.pandey.popcorn.news.data.Article;

import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<Article> articleList;

    public NewsAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_news_feed, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = articleList.get(position);

        Glide.with(context)
                .load(article.getUrlToImage())
                .into(holder.newsImageView);
        holder.newsTitle.setText(article.getTitle());
        holder.newsAuthor.setText(article.getAuthor());
        holder.newsPublishedDate.setText(article.getPublishedAt());
        holder.newsDescription.setText(article.getDescription());
        holder.newsContentLink.setText(article.getContent());
    }

    @Override
    public int getItemCount() {
      return articleList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_image)
        ImageView newsImageView;

        @BindView(R.id.news_title)
        TextView newsTitle;

        @BindView(R.id.news_description)
        TextView newsDescription;

        @BindView(R.id.published_date)
        TextView newsPublishedDate;

        @BindView(R.id.news_author)
        TextView newsAuthor;

        @BindView(R.id.news_content_link)
        TextView newsContentLink;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
