package com.pandey.popcorn4.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.pandey.popcorn4.data.network.db.FvrtMoviesDbObject;


@Database(entities = {FvrtMoviesDbObject.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FvrtMoviesDao fvrtMoviesDao();

    public static AppDatabase appDatabase(@NonNull Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                "fvrt_movies")
                .build();
    }
}
