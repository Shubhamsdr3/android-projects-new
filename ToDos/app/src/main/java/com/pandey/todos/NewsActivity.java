//package com.pandey.todos;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.reflect.TypeToken;
//import com.pandey.todos.adapters.NewsFeedAdapter;
//import com.pandey.todos.model.NewsFeed;
//import com.pandey.todos.repository.RetrofitClient;
//
//import java.lang.reflect.Type;
//import java.util.List;
//
//
//public class NewsActivity extends AppCompatActivity
//{
//    private NewsFeedAdapter newsFeedAdapter;
//
//    private RecyclerView recyclerView;
//
//    private ProgressDialog progressDialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_news);
//
//        progressDialog  = new ProgressDialog(NewsActivity.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//
//        NewsFeedService newsFeedService = RetrofitClient.getRetrofitInstance().create(NewsFeedService.class);
//
//        Call<JsonObject> newsFeedList =
//                newsFeedService.getTopHeadlines("us", "f3d7fd0be82b432abd0da10f1fbc833e" );
//
//        newsFeedList.enqueue(new Callback<JsonObject>()
//        {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
//            {
//                if(response.isSuccessful()) {
//                    progressDialog.dismiss();
//                    String jsonString = response.body().toString();
//                    Log.i("onResponse", jsonString);
//                    Type listType = new TypeToken<List<NewsFeed>>() {
//                    }.getType();
//                    List<NewsFeed> newsFeedList = new Gson().fromJson(jsonString, listType);
//                    generateDataList(newsFeedList);
//                    Log.i("onResponse", newsFeedList.toString());
//                }
//                else
//                {
//                    Toast.makeText(NewsActivity.this, "Problem in loading data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t)
//            {
//                progressDialog.dismiss();
//                t.printStackTrace();
//                Log.e("onFailure", t.toString());
//            }
//        });
//    }
//
//    private void generateDataList(List<NewsFeed> newsFeedList) {
//        recyclerView = findViewById(R.id.news_list);
//        newsFeedAdapter = new NewsFeedAdapter(this, newsFeedList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NewsActivity.this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(newsFeedAdapter);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (progressDialog != null) {
//            progressDialog.dismiss();
//            progressDialog = null;
//        }
//    }
//}