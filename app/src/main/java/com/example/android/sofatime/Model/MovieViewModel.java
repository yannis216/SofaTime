package com.example.android.sofatime.Model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.sofatime.Persistence.MovieDatabase;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> localMovies;
    private List<Movie> apiMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase database = MovieDatabase.getAppDatabase(this.getApplication());
        Log.e("Viewmodel", "Actively getting data from database");
        localMovies = database.movieDao().getMovies();
        apiMovies = loadMovieData();
    }

    public LiveData<List<Movie>> getLocalMovies() {
        return localMovies;
    }

    public List<Movie> getApiMovies(){
        return apiMovies;
    }

    private void loadMovieData(){

    }


}
