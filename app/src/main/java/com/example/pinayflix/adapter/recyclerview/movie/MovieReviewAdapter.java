package com.example.pinayflix.adapter.recyclerview.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pinayflix.R;
import com.example.pinayflix.databinding.ItemReviewBinding;
import com.example.pinayflix.model.datamodel.review.Review;
import com.example.pinayflix.utitlies.Utils;

import java.util.List;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewViewHolder> {
    private List<Review> data;

    public MovieReviewAdapter(List<Review> data) {
        this.data = data;
    }
    public void insertNewReviews(List<Review> reviews){
        data.addAll(reviews);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MovieReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        ItemReviewBinding binder = ItemReviewBinding.bind(view);
        return new MovieReviewViewHolder(binder);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewViewHolder holder, int position) {
        Review review = data.get(position);
        holder.userName.setText(review.getAuthor());
        holder.ratingBar.setRating((float) review.getAuthorDetails().getRating() / 2);
        holder.content.setText(String.format("\"%s\"", review.getContent()));

        Glide.with(holder.itemView.getContext()).load(Utils.POSTER_PATH + review.getAuthorDetails().getAvatarPath())
                .into(holder.avatar).onLoadFailed(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_person));


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MovieReviewViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView userName;
        private RatingBar ratingBar;
        private TextView content;

        public MovieReviewViewHolder(@NonNull ItemReviewBinding binder) {
            super(binder.getRoot());
            avatar = binder.avatarIv;
            userName = binder.authorName;
            ratingBar = binder.authorRating;
            content = binder.content;
        }
    }
}
