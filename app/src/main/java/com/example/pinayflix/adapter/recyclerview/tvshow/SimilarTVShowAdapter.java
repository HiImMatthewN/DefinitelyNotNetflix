package com.example.pinayflix.adapter.recyclerview.tvshow;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.pinayflix.R;
import com.example.pinayflix.databinding.ItemSimilarTvShowsBinding;
import com.example.pinayflix.model.datamodel.tvshow.TVShow;
import com.example.pinayflix.utitlies.Utils;

import java.util.List;

public class SimilarTVShowAdapter extends RecyclerView.Adapter<SimilarTVShowAdapter.SimilarTVShowViewHolder> {
    private List<TVShow> data;
    private String TAG = "SimilarTVShowAdapter";
    private RequestManager requestManager;

    public SimilarTVShowAdapter(List<TVShow> tvShows, RequestManager requestManager) {
        this.data = tvShows;
        this.requestManager = requestManager;

    }

    @NonNull
    @Override
    public SimilarTVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_similar_tv_shows, parent, false);
        ItemSimilarTvShowsBinding binder = ItemSimilarTvShowsBinding.bind(view);
        return new SimilarTVShowViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarTVShowViewHolder holder, int position) {

        TVShow tvShow = data.get(position);
        ImageView target = holder.posterImageView;

        requestManager
                .load(Uri.parse(Utils.POSTER_PATH + tvShow.getPosterPath()))
                .into(target);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SimilarTVShowViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImageView;

        public SimilarTVShowViewHolder(@NonNull ItemSimilarTvShowsBinding binder) {
            super(binder.getRoot());
            posterImageView = binder.moviePosterIv;
        }

    }

}
