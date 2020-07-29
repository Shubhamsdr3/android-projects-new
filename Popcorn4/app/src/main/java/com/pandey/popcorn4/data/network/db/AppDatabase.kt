package com.pandey.popcorn4.data.network.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pandey.popcorn4.db.FvrtMoviesDao

@Database(entities = [FvrtMoviesDbObject::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun fvrtMoviesDao(): FvrtMoviesDao?

    companion object {
        fun appDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "fvrt_movies")
                    .build()
            }
        }
}
