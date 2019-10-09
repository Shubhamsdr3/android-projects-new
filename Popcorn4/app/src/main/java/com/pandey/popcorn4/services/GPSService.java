package com.pandey.popcorn4.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class GPSService extends Service {

    private LocationManager location = null;
    private NotificationManager notifier = null;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        notifier = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (flags != 0) {
            Log.w("GPSService", "Redelivered or retrying service start: " + flags);
        }
//        doServiceStart(intent, startId);
        return Service.START_REDELIVER_INTENT;
    }

//    @Override
//    public void doServiceStart(Intent intent, int startId) {
//        int updateRate = intent.getIntExtra(EXTRA_UPDATE_RATE, -1);
//        if (updateRate == -1) {
//            updateRate = 60000;
//        }
//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.NO_REQUIREMENT);
//        criteria.setPowerRequirement(Criteria.POWER_LOW);
//
//        location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        String best = location.getBestProvider(criteria, true);
//        location.requestLocationUpdates(best, updateRate, 0, trackListener);
//        Notification notify = new Notification(android.R.drawable.stat_notify_more, "GPS Tracking", System.currentTimeMillis());
//        notify.flags |= Notification.FLAG_AUTO_CANCEL;
//        Intent toLaunch = new Intent(getApplicationContext(), ServiceControl.class);
//        PendingIntent intentBack = PendingIntent.getActivity(getApplicationContext(), 0, toLaunch, 0);
//        notify.setLatestEventInfo(getApplicationContext(), "GPS Tracking", "Tracking start at " + updateRate + "ms intervals with [" + best +
//                "] as the provider.", intentBack);
//        notifier.notify(GPS_NOTIFY, notify);
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (location != null) {
//            location.removeUpdates(trackListener); location = null;
//        }
    }
}
