package com.pandey.todos.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {TaskEntry.class, User.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverters.class)
public abstract class AppDatabase  extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "todolist";
    private static AppDatabase sintance;

    public static AppDatabase getInstance(Context context) {
        if (sintance == null) {
            synchronized (LOCK) {
                sintance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
                Log.d(LOG_TAG, "Creating new database..");
            }
        }
        Log.d(LOG_TAG, "Getting database instance.." + sintance);
        return sintance;
    }

    @Nullable
    public abstract TaskDao taskDao();
}