package com.pandey.popcorn4.data.network.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FvrtMoviesDbObject")
data class FvrtMoviesDbObject(

        @PrimaryKey
        @ColumnInfo(name = "movieId")
        var movieId: Int,

        @ColumnInfo(name = "moviePoster")
        var moviePosterPath: String,

        @ColumnInfo(name = "movieTitle")
        var movieTitle: String?,

        @ColumnInfo(name = "movieDescription")
        var movieDescription: String,

        @ColumnInfo(name = "movieCount")
        var movieStarCount: Int,

        @ColumnInfo(name = "language")
        var movieLanguage : String,

        @ColumnInfo(name = "createdAt")
        var timestamp: Long = 0
)
