package com.example.android.sofatime.Persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.sofatime.Model.Movie;

import java.util.List;


@Dao
public interface movieDao{
    @Query ("SELECT * FROM movies")
    LiveData<List<Movie>> getMovies();

    @Query ("SELECT * FROM movies WHERE id = :id")
    Movie getMovie(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);
}
