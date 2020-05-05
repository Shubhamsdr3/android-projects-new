package com.pandey.popcorn4.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pandey.popcorn4.data.network.db.FvrtMoviesDbObject;

import java.util.List;

import io.reactivex.Flowable;


@Dao
public interface FvrtMoviesDao {

    @Insert
    void insert(FvrtMoviesDbObject fvrtMoviesDbObject);

    @Query("SELECT * FROM FvrtMoviesDbObject")
    Flowable<List<FvrtMoviesDbObject>> getAllFvrtMovies();

    @Query("SELECT * FROM FvrtMoviesDbObject WHERE movieId = :movieId")
    FvrtMoviesDbObject getMovie(int movieId);

}
