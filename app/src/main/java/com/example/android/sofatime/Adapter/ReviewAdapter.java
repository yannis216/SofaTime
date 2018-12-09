package com.example.android.sofatime.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sofatime.Model.MovieReview;
import com.example.android.sofatime.R;

import java.util.ArrayList;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {
    private ArrayList<MovieReview> reviews;
    private LayoutInflater mInflater;
    private Context context;


    public ReviewAdapter(Context context, ArrayList<MovieReview> reviewList){
        this.mInflater = LayoutInflater.from(context);
        this.reviews = reviewList;
        this.context =context;
    }

    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder{

        public ReviewAdapterViewHolder(View view) {
            super(view);
        }

    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder reviewAdapterViewHolder, int position) {
        MovieReview currentReview = reviews.get(position);

        final String reviewAuthorName = currentReview.getAuthor();
        String reviewContent = currentReview.getContent();

        TextView authorNameView = reviewAdapterViewHolder.itemView.findViewById(R.id.tv_review_author_name);
        TextView reviewContentView = reviewAdapterViewHolder.itemView.findViewById(R.id.tv_review_content);


        authorNameView.setText(reviewAuthorName);
        reviewContentView.setText(reviewContent);

    }
}
