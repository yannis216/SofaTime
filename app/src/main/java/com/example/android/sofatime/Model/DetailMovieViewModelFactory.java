package com.example.android.sofatime.Model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.sofatime.Persistence.MovieDatabase;

// This whole thing here is needed when you want a Viewmodel to receive ids (or other params) to get specific values)

public class DetailMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MovieDatabase mDb;
    private final int mMovieId;


    public DetailMovieViewModelFactory(MovieDatabase database, int movieId) {
        mDb = database;
        mMovieId = movieId;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailMovieViewModel(mDb, mMovieId);
    }
}
