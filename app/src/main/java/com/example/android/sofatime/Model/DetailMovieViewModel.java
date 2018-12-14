package com.example.android.sofatime.Model;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.sofatime.Persistence.MovieDatabase;

public class DetailMovieViewModel extends ViewModel {
    private Movie movie;

    public DetailMovieViewModel(final MovieDatabase database,final int id){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                movie = database.movieDao().getMovie(id);
                Log.e("DetailViewModel", "DATABASE has been requested for single Movie");
            }
        });
        thread.start();

    }

    public Movie getMovie() {
        return movie;
    }

}
