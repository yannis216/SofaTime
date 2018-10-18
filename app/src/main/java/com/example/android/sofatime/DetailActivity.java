package com.example.android.sofatime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    
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
    }
}
