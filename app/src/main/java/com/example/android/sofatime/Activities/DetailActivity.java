package com.example.android.sofatime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sofatime.Adapter.MovieAdapter;
import com.example.android.sofatime.Adapter.TrailerAdapter;
import com.example.android.sofatime.Model.Movie;
import com.example.android.sofatime.Model.MovieTrailer;
import com.example.android.sofatime.Model.MovieTrailerList;
import com.example.android.sofatime.Network.RetrofitClientInstance;
import com.example.android.sofatime.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TrailerAdapter mTrailerAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Movie detailedMovie = (Movie) intent.getSerializableExtra("requestedMovie");

        //getting all the views
        ImageView posterDetailView = findViewById(R.id.iv_poster_detail);
        TextView titleDetailView = findViewById(R.id.tv_title_detail);
        TextView releaseDateDetailView = findViewById(R.id.tv_releasedate_detail);
        TextView ratingDetailView = findViewById(R.id.tv_rating_detail);
        TextView overviewDetailView = findViewById(R.id.tv_overview_detail);

        //getting necessary String Resources
        String rating_firstPart = getResources().getString(R.string.movie_rating);
        String release_firstPart = getResources().getString(R.string.movie_release);

        //setting all the data to the views
        titleDetailView.setText(detailedMovie.getTitle());
        releaseDateDetailView.setText(release_firstPart + detailedMovie.getReleaseDate());
        ratingDetailView.setText(rating_firstPart + detailedMovie.getVoteAverage().toString());
        overviewDetailView.setText(detailedMovie.getOverview());

        String imageUrl = MovieAdapter.BASE_IMG_URL +detailedMovie.getPosterPath();
        Picasso picasso = Picasso.with(posterDetailView.getContext());
        picasso.setLoggingEnabled(true);
        picasso.load(imageUrl).into(posterDetailView);

        loadTrailerData(detailedMovie.getId());

    }

    public void loadTrailerData(int id){
        RetrofitClientInstance.GetSpecificTrailersService service = RetrofitClientInstance.getRetrofitInstance(this ).create(RetrofitClientInstance.GetSpecificTrailersService.class);
        Call<MovieTrailerList> call = service.getSpecificTrailers(id);

        call.enqueue(new Callback<MovieTrailerList>() {

            @Override
            public void onResponse(Call<MovieTrailerList> call, Response<MovieTrailerList> response) {
                generateDataList(response.body());

            }

            @Override
            public void onFailure(Call<MovieTrailerList> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Please turn on Internet Connection :-)" , Toast.LENGTH_SHORT).show();
                Log.e("Tag", "This is the Throwable:", t);
            }
        });
    }

    //Initiates that the adapter does its work :-)
    private void generateDataList(MovieTrailerList trailers){
        mRecyclerView = findViewById(R.id.rv_trailer_list);
        ArrayList<MovieTrailer> trailerList = trailers.getTrailers();
        Log.e("TRAILERS", trailerList.toString());

        mRecyclerView.setNestedScrollingEnabled(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTrailerAdapter = new TrailerAdapter(this, trailerList);
        mRecyclerView.setAdapter(mTrailerAdapter);

    }
}
