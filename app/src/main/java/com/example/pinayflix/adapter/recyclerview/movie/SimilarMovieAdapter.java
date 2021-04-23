package com.example.pinayflix.adapter.recyclerview.movie;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.pinayflix.R;
import com.example.pinayflix.databinding.ItemSimilarTvShowsBinding;
import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.utitlies.Utils;

import java.util.List;

public class SimilarMovieAdapter extends RecyclerView.Adapter<SimilarMovieAdapter.SimilarMovieViewHolder> {
    private List<Movie> data;
    private String TAG = "SimilarTVShowAdapter";
    private RequestManager requestManager;

    public SimilarMovieAdapter(List<Movie> tvShows, RequestManager requestManager) {
        this.data = tvShows;
        this.requestManager = requestManager;

    }

    @NonNull
    @Override
    public SimilarMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_similar_tv_shows, parent, false);
        ItemSimilarTvShowsBinding binder = ItemSimilarTvShowsBinding.bind(view);
        return new SimilarMovieViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarMovieViewHolder holder, int position) {

        Movie tvShow = data.get(position);
        ImageView target = holder.posterImageView;

        requestManager
                .load(Uri.parse(Utils.POSTER_PATH + tvShow.getPosterPath())).transform(new CenterCrop())
                .into(target);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SimilarMovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImageView;

        public SimilarMovieViewHolder(@NonNull ItemSimilarTvShowsBinding binder) {
            super(binder.getRoot());
            posterImageView = binder.moviePosterIv;
        }

    }

}
