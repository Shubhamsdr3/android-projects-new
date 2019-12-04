package com.pandey.popcorn4.deeplinks;

import androidx.core.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.pandey.popcorn4.HomeActivity;

public class DeeplinkUtils {

    @NonNull
    public static TaskStackBuilder homeRedirectionStack(Context context) {
        Intent parentIntent =  new Intent(context, HomeActivity.class);
        TaskStackBuilder  taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntent(parentIntent);
        return taskStackBuilder;
    }
}
