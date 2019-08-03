//package com.pandey.todos.adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.pandey.todos.R;
//import com.pandey.todos.model.NewsFeed;
//
//import java.util.List;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsViewHolder>
//{
//
//    private Context context;
//    private List<NewsFeed> newsFeedList;
//
//    public NewsFeedAdapter(Context context, List<NewsFeed> newsFeedList)
//    {
//        this.context = context;
//        this.newsFeedList = newsFeedList;
//    }
//
//    @NonNull
//    @Override
//    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//    {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.layout_news_feed, parent, false);
//        return new  NewsViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position)
//    {
////        holder.urlToImage.setImageResource(newsFeedList.get(position).getImageUrl());
//        holder.newsTitle.setText(newsFeedList.get(position).getTitle());
//        holder.authorName.setText(newsFeedList.get(position).getAuthor());
//        holder.publishedDate.setText(newsFeedList.get(position).getPublishedAt());
//        holder.newsContent.setText(newsFeedList.get(position).getContent());
//        holder.newsDescription.setText(newsFeedList.get(position).getDescription());
//    }
//
//    @Override
//    public int getItemCount()
//    {
//        return newsFeedList.size();
//    }
//
//    class NewsViewHolder extends RecyclerView.ViewHolder
//    {
//        TextView authorName;
//        TextView newsTitle;
//        TextView newsDescription;
//        TextView newsUrl;
//        ImageView urlToImage;
//        TextView publishedDate;
//        TextView newsContent;
//
//
//        public NewsViewHolder(@NonNull View itemView)
//        {
//            super(itemView);
//
//            authorName  = itemView.findViewById(R.id.news_author);
//            newsTitle = itemView.findViewById(R.id.news_title);
//            newsDescription = itemView.findViewById(R.id.news_description);
////            newsUrl = itemView.findViewById(R.id.news)
//            urlToImage = itemView.findViewById(R.id.news_image);
//            publishedDate = itemView.findViewById(R.id.published_time);
//            newsContent = itemView.findViewById(R.id.news_content);
//
//        }
//    }
//
//}
