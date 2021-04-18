package com.example.pinayflix.adapter.recyclerview.tvshow;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.pinayflix.R;
import com.example.pinayflix.databinding.ItemSimilarTvShowsBinding;
import com.example.pinayflix.model.datamodel.movie.Movie;

import java.util.List;

public class SimilarMovieAdapter extends RecyclerView.Adapter<SimilarMovieAdapter.SimilarMovieViewHolder> {
    private List<Movie> data;
    private String TAG = "SimilarTVShowAdapter";
    private final String IMAGE_PATH = "https://image.tmdb.org/t/p/w342";
    public SimilarMovieAdapter(List<Movie> tvShows) {
        this.data = tvShows;

    }

    @NonNull
    @Override
    public SimilarMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_similar_tv_shows,parent,false);
        ItemSimilarTvShowsBinding binder = ItemSimilarTvShowsBinding.bind(view);
        return new SimilarMovieViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarMovieViewHolder holder, int position) {

        Movie tvShow = data.get(position);
        ImageView target = holder.posterImageView;

        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(IMAGE_PATH+tvShow.getPosterPath())).transform(new CenterCrop())
                .into(target);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SimilarMovieViewHolder extends RecyclerView.ViewHolder{
        private ImageView posterImageView;

        public SimilarMovieViewHolder(@NonNull ItemSimilarTvShowsBinding binder) {
            super(binder.getRoot());
            posterImageView = binder.moviePosterIv;
        }

    }

}
