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
import com.example.pinayflix.model.datamodel.tvshow.TVShow;

import java.util.List;

public class SimilarTVShowAdapter extends RecyclerView.Adapter<SimilarTVShowAdapter.SimilarTVShowViewHolder> {
    private List<TVShow> data;
    private String TAG = "SimilarTVShowAdapter";
    private final String IMAGE_PATH = "https://image.tmdb.org/t/p/w342";
    public SimilarTVShowAdapter(List<TVShow> tvShows) {
        this.data = tvShows;

    }

    @NonNull
    @Override
    public SimilarTVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_similar_tv_shows,parent,false);
        ItemSimilarTvShowsBinding binder = ItemSimilarTvShowsBinding.bind(view);
        return new SimilarTVShowViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarTVShowViewHolder holder, int position) {

        TVShow tvShow = data.get(position);
        ImageView target = holder.posterImageView;

        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(IMAGE_PATH+tvShow.getPosterPath())).transform(new CenterCrop())
                .into(target);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SimilarTVShowViewHolder extends RecyclerView.ViewHolder{
        private ImageView posterImageView;

        public SimilarTVShowViewHolder(@NonNull ItemSimilarTvShowsBinding binder) {
            super(binder.getRoot());
            posterImageView = binder.moviePosterIv;
        }

    }

}
