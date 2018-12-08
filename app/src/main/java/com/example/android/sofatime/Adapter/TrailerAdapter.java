package com.example.android.sofatime.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.sofatime.Model.MovieTrailer;
import com.example.android.sofatime.R;

import java.util.List;


public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {
    private List<MovieTrailer> trailers;
    private LayoutInflater mInflater;

    public interface TrailerAdapterOnClickHandler {
        void onClick(MovieTrailer requestedMovieTrailer);
    }


    public TrailerAdapter(Context context, List<MovieTrailer> trailerList){
        this.mInflater = LayoutInflater.from(context);
        this.trailers = trailerList;
    }

    public int getItemCount() {
        return trailers.size();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TrailerAdapterViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieTrailer requestedTrailer = trailers.get(adapterPosition);
        }
    }

    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailer_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder trailerAdapterViewHolder, int position) {
        MovieTrailer currentTrailer = trailers.get(position);

        String trailerKey = currentTrailer.getKey();
        String trailerName = currentTrailer.getName();

        ImageButton playButton = trailerAdapterViewHolder.itemView.findViewById(R.id.ib_play_trailer);
        TextView nameView = trailerAdapterViewHolder.itemView.findViewById(R.id.tv_trailer_name);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Place Intent here
            }
        });
        nameView.setText(trailerName);

    }
}
