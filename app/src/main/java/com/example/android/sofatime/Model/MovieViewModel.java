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

    private List<Movie> apiPopMovies;
    private List<Movie> apiTopMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase database = MovieDatabase.getAppDatabase(this.getApplication());
        Log.e("Viewmodel", "Actively getting data from database");
        localMovies = database.movieDao().getMovies();
    }

    public LiveData<List<Movie>> getLocalMovies() {
        return localMovies;
    }

    public List<Movie> getApiPopMovies(){
        return apiPopMovies;
    }

    public List<Movie> getApiTopMovies(){
        return apiTopMovies;
    }

    public void setApiPopMovies(List<Movie> apiPopMovies) {
        this.apiPopMovies = apiPopMovies;
    }

    public void setApiTopMovies(List<Movie> apiTopMovies) {
        this.apiTopMovies = apiTopMovies;
    }

}
