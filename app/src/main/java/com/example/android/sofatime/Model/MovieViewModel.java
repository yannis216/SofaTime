package com.example.android.sofatime.Model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.sofatime.Persistence.MovieDatabase;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase database = MovieDatabase.getAppDatabase(this.getApplication());
        Log.e("Viewmodel", "Actively getting data from database");
        movies = database.movieDao().getMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }


}