package com.example.android.sofatime.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.android.sofatime.Model.Movie;

import java.util.ArrayList;

@Dao
public interface movieDao {
    @Query ("SELECT * FROM movies")
    ArrayList<Movie> getMovies();
}
