package com.example.android.sofatime;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private List<Movie> movies;
    private LayoutInflater mInflater;
    public static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/w185/";
    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie requestedMovie);
    }


    public MovieAdapter (Context context, ArrayList<Movie> moviesList, MovieAdapterOnClickHandler clickHandler){
        this.mInflater = LayoutInflater.from(context);
        this.movies = moviesList;
        mClickHandler = clickHandler;
    }

    public int getItemCount() {
        return movies.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MovieAdapterViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie requestedMovie = movies.get(adapterPosition);
            mClickHandler.onClick(requestedMovie);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie currentMovie = movies.get(position);
        String title = currentMovie.getTitle();
        String imageUrl = BASE_IMG_URL + currentMovie.getPosterPath();
        double voteAverageDouble = currentMovie.getVoteAverage();
        float voteAverage =(float) voteAverageDouble;
        String rating = String.valueOf(voteAverage) + "/10";

        TextView titleView = movieAdapterViewHolder.itemView.findViewById(R.id.tv_title);
        TextView ratingView = movieAdapterViewHolder.itemView.findViewById(R.id.tv_rating);
        ImageView posterView = movieAdapterViewHolder.itemView.findViewById(R.id.iv_poster);
        // TODO I really dont understand why this ".itemView." works here(Android studio proposed it). It works! But is it "Best practice" to do it this way?

        //Implementing the visualisation of the rating
        View greenView = movieAdapterViewHolder.itemView.findViewById(R.id.visualRatingGreen);
        View greyView = movieAdapterViewHolder.itemView.findViewById(R.id.visualRatingGrey);
        ((LinearLayout.LayoutParams) greenView.getLayoutParams()).weight = voteAverage;
        ((LinearLayout.LayoutParams) greyView.getLayoutParams()).weight = 10- voteAverage;
        //TODO I tried to add some visualization here - it works only for the first few views, when you scroll down it gets displayed wrongly. Is that because of the recyclement of the views?
        //TODO What would be the appropriate way to handle this functionality?


        titleView.setText(title);
        ratingView.setText(rating);

        Picasso picasso = Picasso.with(posterView.getContext());
        picasso.setLoggingEnabled(true);
        picasso.load(imageUrl).into(posterView);
    }
}
