package com.example.android.sofatime.Activities;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.sofatime.Adapter.MovieAdapter;
import com.example.android.sofatime.Model.Movie;
import com.example.android.sofatime.Model.Movies;
import com.example.android.sofatime.Network.RetrofitClientInstance;
import com.example.android.sofatime.Persistence.MovieDatabase;
import com.example.android.sofatime.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {


    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private MovieDatabase movieDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        movieDatabase = MovieDatabase.getAppDatabase(this);

        String PopOrTop = "Pop"; // "Default setting because I like to see the Popular movies as default (And yes I know I could let the user set this via preferences :-) )"
        loadMovieData(PopOrTop);
    }

    @Override
    public void onClick(Movie requestMovie){
        Context context = this;

        Intent startDetailActivityIntent = new Intent(context, DetailActivity.class);
        startDetailActivityIntent.putExtra("requestedMovie", requestMovie);
        startActivity(startDetailActivityIntent);
    }

    //Initiates that the adapter does its work :-)
    private void generateDataList(List<Movie> movies){
        mRecyclerView = findViewById(R.id.rv_gridview);
        int numberOfColumns = 2;
        LiveData<List<Movie>> liveMovies = movies;


        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        mMovieAdapter = new MovieAdapter(this, movies, this);
        mRecyclerView.setAdapter(mMovieAdapter);

    }

    public void loadMovieData(String PopOrTop){

        //TODO Maybe insert here a logic based on internet access retrieve from Room DB
        Call<Movies> call = PopOrTopFinder(PopOrTop);
        call.enqueue(new Callback<Movies>() {

            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                List<Movie> movies = response.body().getResults();
                LiveData<List<Movie>> liveMovies = movies;
                generateDataList(movies);
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Toast.makeText(GridActivity.this, "We can only show your favourite movies as your internet connection seems to be turned off" , Toast.LENGTH_LONG).show();
                Log.e("Tag", "This is the Throwable:", t);
                //TODO Maybe even here :-)
                generateDataList(getFavMoviesFromDb(movieDatabase));

            }
        });

    }

    //Method that generates (not executes) a call to retrofit based on the user preferences
    public Call<Movies> PopOrTopFinder(String PopOrTop){
        switch (PopOrTop){
            case "Top":
                /*Create handle for the Retrofit magic*/
                RetrofitClientInstance.GetTopRatedMoviesService service2 = RetrofitClientInstance.getRetrofitInstance(this).create(RetrofitClientInstance.GetTopRatedMoviesService.class);
                return service2.getTopRatedMovies();
            default:
                /*Create handle for the Retrofit magic*/
                RetrofitClientInstance.GetPopularMoviesService service1 = RetrofitClientInstance.getRetrofitInstance(this ).create(RetrofitClientInstance.GetPopularMoviesService.class);
                return service1.getPopularMovies();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.preferences, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.preference_pop:{
                String PopOrTop= "Pop";
                loadMovieData(PopOrTop);
                break;}

            case R.id.preference_top:{
                String PopOrTop= "Top";
                loadMovieData(PopOrTop);
                break;}
        }
        return super.onOptionsItemSelected(item);
    }

    public LiveData<List<Movie>> getFavMoviesFromDb(MovieDatabase db){
        LiveData<List<Movie>> movies = db.movieDao().getMovies();
        return movies;
    }


}
