package com.example.android.sofatime.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sofatime.Adapter.MovieAdapter;
import com.example.android.sofatime.Adapter.ReviewAdapter;
import com.example.android.sofatime.Adapter.TrailerAdapter;
import com.example.android.sofatime.Model.Movie;
import com.example.android.sofatime.Model.MovieReview;
import com.example.android.sofatime.Model.MovieReviewList;
import com.example.android.sofatime.Model.MovieTrailer;
import com.example.android.sofatime.Model.MovieTrailerList;
import com.example.android.sofatime.Network.RetrofitClientInstance;
import com.example.android.sofatime.Persistence.MovieDatabase;
import com.example.android.sofatime.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewTrailers;
    private RecyclerView mRecyclerViewReviews;
    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;

    private static final String DATABASE_NAME = "movies_db";
    private MovieDatabase movieDatabase;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieDatabase = MovieDatabase.getAppDatabase(this);

        Intent intent = getIntent();
        final Movie detailedMovie = (Movie) intent.getSerializableExtra("requestedMovie"); //TODO I do not believe that final can work here
        //I do understand why this has to be final - But I do not understand why it works: Arent final variables unable to change? But I am changing it with the onclick method?

        //getting all the views
        ImageView posterDetailView = findViewById(R.id.iv_poster_detail);
        TextView titleDetailView = findViewById(R.id.tv_title_detail);
        TextView releaseDateDetailView = findViewById(R.id.tv_releasedate_detail);
        TextView ratingDetailView = findViewById(R.id.tv_rating_detail);
        final ImageButton starView = findViewById(R.id.ib_star);

        starView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (detailedMovie.isStarred()){
                    detailedMovie.setStarred(false);
                    Log.e("StarValue", ""+ detailedMovie.isStarred());
                    //TODO DELETE Movie from Room DB
                    deleteMovie(movieDatabase, detailedMovie);
                }
                else{
                    detailedMovie.setStarred(true);
                    Log.e("StarValue", ""+ detailedMovie.isStarred());
                    //TODO Add Movie to Room Db
                    addMovieToDb(movieDatabase, detailedMovie);




                }
                boolean isStarred = detailedMovie.isStarred();

                setImageForStar(isStarred, starView);

            }
        });



        TextView overviewDetailView = findViewById(R.id.tv_overview_detail);

        //getting necessary String Resources
        String rating_firstPart = getResources().getString(R.string.movie_rating);
        String release_firstPart = getResources().getString(R.string.movie_release);

        //setting all the data to the views
        titleDetailView.setText(detailedMovie.getTitle());
        releaseDateDetailView.setText(release_firstPart + detailedMovie.getReleaseDate());
        ratingDetailView.setText(rating_firstPart + detailedMovie.getVoteAverage().toString());
        overviewDetailView.setText(detailedMovie.getOverview());

        //Take care of isStarred on initial load
        boolean isStarred = detailedMovie.isStarred();
        setImageForStar(isStarred, starView);
        //TODO Have a look at this when Room is implemented



        //display the poster picture
        String imageUrl = MovieAdapter.BASE_IMG_URL +detailedMovie.getPosterPath();
        Picasso picasso = Picasso.with(posterDetailView.getContext());
        picasso.setLoggingEnabled(true);
        picasso.load(imageUrl).into(posterDetailView);



        loadTrailerData(detailedMovie.getId());
        loadReviewData(detailedMovie.getId());

    }

    //METHODS FOR TRAILER
    public void loadTrailerData(int id){
        RetrofitClientInstance.GetSpecificTrailersService service = RetrofitClientInstance.getRetrofitInstance(this ).create(RetrofitClientInstance.GetSpecificTrailersService.class);
        Call<MovieTrailerList> call = service.getSpecificTrailers(id);

        call.enqueue(new Callback<MovieTrailerList>() {

            @Override
            public void onResponse(Call<MovieTrailerList> call, Response<MovieTrailerList> response) {
                generateTrailerDataList(response.body());

            }

            @Override
            public void onFailure(Call<MovieTrailerList> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Please turn on Internet Connection :-)" , Toast.LENGTH_SHORT).show();
                Log.e("Tag", "This is the Throwable:", t);

            }
        });
    }


    //Initiates that the adapter does its work :-)
    private void generateTrailerDataList(MovieTrailerList trailers){
        mRecyclerViewTrailers = findViewById(R.id.rv_trailer_list);
        ArrayList<MovieTrailer> trailerList = trailers.getTrailers();
        Log.e("TRAILERS", trailerList.toString());

        mRecyclerViewTrailers.setNestedScrollingEnabled(false);

        mRecyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        mTrailerAdapter = new TrailerAdapter(this, trailerList);
        mRecyclerViewTrailers.setAdapter(mTrailerAdapter);

    }

    //METHODS FOR REVIEWS
    public void loadReviewData(int id){
        RetrofitClientInstance.GetSpecificReviewsService service = RetrofitClientInstance.getRetrofitInstance(this ).create(RetrofitClientInstance.GetSpecificReviewsService.class);
        Call<MovieReviewList> call = service.getSpecificReviews(id);

        call.enqueue(new Callback<MovieReviewList>() {

            @Override
            public void onResponse(Call<MovieReviewList> call, Response<MovieReviewList> response) {
                generateReviewDataList(response.body());

            }

            @Override
            public void onFailure(Call<MovieReviewList> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Please turn on Internet Connection :-)" , Toast.LENGTH_SHORT).show();
                Log.e("Tag", "This is the Throwable:", t);
            }
        });
    }

    //Initiates that the adapter does its work :-)
    private void generateReviewDataList(MovieReviewList reviews){
        mRecyclerViewReviews = findViewById(R.id.rv_review_list);
        ArrayList<MovieReview> reviewList = reviews.getMovieReviews();
        Log.e("REVIEWS", reviewList.toString());

        mRecyclerViewReviews.setNestedScrollingEnabled(false);

        mRecyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        mReviewAdapter = new ReviewAdapter(this, reviewList);
        mRecyclerViewReviews.setAdapter(mReviewAdapter);

    }

    private void setImageForStar(boolean isStarred, ImageButton starView){  //TODO Have a look at this when Room is implemented
        //Taking care of the Imagebutton
        if(isStarred){
            starView.setImageResource(R.drawable.baseline_star_black_18dp);
            starView.setColorFilter(Color.argb(255,255,255,0));
        }
        else{
            starView.setImageResource((R.drawable.baseline_star_border_black_18dp));
            starView.setColorFilter(Color.argb(255,255,255,255));
        }
    }

    //Database Methods

    private static Movie addMovieToDb(final MovieDatabase db, Movie movie) {
        db.movieDao().insertMovie(movie);
        Log.e("addMovietoDb", "has been called with" + movie.toString());
        return movie;
    }

    private static void deleteMovie(final  MovieDatabase db, Movie movie){
        db.movieDao().deleteMovie(movie);
        Log.e("deleteMovie", "has been called on" + movie.toString());

    }

    }
