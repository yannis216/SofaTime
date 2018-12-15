package com.example.android.sofatime.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.sofatime.Persistence.MovieDatabase;

public class DetailMovieViewModel extends ViewModel {
    private LiveData<Movie> movie;

    public DetailMovieViewModel(final MovieDatabase database,final int id){
            movie = database.movieDao().getMovie(id);
            Log.e("DetailViewModel", "DATABASE has been requested for single Movie");


    }

    public LiveData<Movie> getMovie() {
        return movie;
    }

}
