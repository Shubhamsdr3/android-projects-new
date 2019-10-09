package com.pandey.popcorn4.notifications;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.pandey.popcorn4.HomeActivity;
import com.pandey.popcorn4.R;
import com.pandey.popcorn4.notifications.data.NotificationDto;

import timber.log.Timber;


/**
 * Our own NotificationManger.
 * Don't be confuse with {@link android.app.NotificationManager}
 */

public class NotificationManager {

    private static final String POP_CORN_CHANNEL_ID = "pop_corn_channel_id";
    private static final String EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID";

    @NonNull
    private Context mContext;

    private static int notificationId = (int) (Math.random()*10000);

    NotificationManager(@NonNull Context context) {
        this.mContext = context;
    }

    /**
     * Call back method when app is in foreground
     * @param remoteMessage : Message received when user gets notification.
     */
    void notifyRemoteMessage(@NonNull RemoteMessage remoteMessage) {
        Timber.d("Working on notification received...: %s",  remoteMessage.toString());
        // default notification from firebase console
        if (remoteMessage.getNotification() == null || remoteMessage.getData().size() == 0) {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setTitle(remoteMessage.getNotification().getTitle());
            notificationDto.setContent(remoteMessage.getNotification().getBody());
            // Setting action on notification tap.
            Intent intent = new Intent(mContext, HomeActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    mContext,
                    0,
                    intent,
                    PendingIntent.FLAG_CANCEL_CURRENT // To cancel the current pending and override with new.
            );

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, POP_CORN_CHANNEL_ID)
                    .setContentTitle(notificationDto.getTitle())
                    .setContentText(notificationDto.getContent())
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setSmallIcon(R.drawable.ic_popcorn)
                    .setAutoCancel(true)
                    .setWhen(0)
                    //To make expandable.
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(remoteMessage.getNotification().getBody()));
            notificationBuilder.setContentIntent(pendingIntent);
            android.app.NotificationManager notificationManager =
                    (android.app.NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId++, notificationBuilder.build());
            return;
        }
        // Our custom notification.
        createCustomNotification(remoteMessage);
    }

    private void createCustomNotification(@NonNull RemoteMessage remoteMessage) {
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, POP_CORN_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.ic_popcorn)
                .setAutoCancel(true)
                //To make expandable.
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body));

        // Adding actions to notifications
        Intent snoozeIntent = new Intent(mContext, HomeActivity.class);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, notificationId++);
        PendingIntent snoozePendingIntent =
                PendingIntent.getActivity(mContext, 0, snoozeIntent, 0);
        notificationBuilder.addAction(
                R.drawable.ic_search, mContext.getResources().getString(R.string.snooze),
                snoozePendingIntent
        );
        notificationBuilder.setContentIntent(snoozePendingIntent);
        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "PopCorn";
            String description = "Notifications for popcorn";
            int importance = NotificationCompat.PRIORITY_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(name, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(mChannel);
        }

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.layout_news_feed);
        remoteViews.setTextViewText(R.id.shadow, "Hame kya pta ki ye kya hai ?");
        notificationBuilder.setCustomBigContentView(remoteViews);
        notificationManager.notify(notificationId++, notificationBuilder.build());
    }
}
