package com.pandey.popcorn4.worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.pandey.popcorn4.R;
import com.pandey.popcorn4.moviedetails.MovieDetailsActivity;

import timber.log.Timber;

import static com.pandey.popcorn4.movie.PopularMoviesFragment.MOVIE_OVERVIEW;
import static com.pandey.popcorn4.movie.PopularMoviesFragment.MOVIE_TITLE;


public class NotifyUserWorker extends Worker {

    public NotifyUserWorker(@NonNull Context context,
                            @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Result doWork() {
        Timber.i("So notification is working...");
        String movieTitle = getInputData().getString(MOVIE_TITLE);
        String movieOverview = getInputData().getString(MOVIE_OVERVIEW);
        showMovieNotification(movieTitle, movieOverview);
        return Result.success();
    }

    private void showMovieNotification(@Nullable String movieTitle, @Nullable String movieOverview) {
        NotificationManager manager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "task_channel";
        String channelName = "task_name";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        Intent resultIntent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        resultIntent.setAction(Intent.ACTION_VIEW);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(movieTitle)
                .setContentText(movieOverview)
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.drawable.ic_popcorn);
        manager.notify(1, builder.build());
    }




}
