package com.example.pinayflix.adapter.recyclerview.tvshow;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.pinayflix.R;
import com.example.pinayflix.databinding.ItemEpisodeBinding;
import com.example.pinayflix.model.datamodel.tvshow.Episode;

import java.util.List;

public class EpisodeTVShowAdapter extends RecyclerView.Adapter<EpisodeTVShowAdapter.EpisodeTVShowViewHolder> {
    private List<Episode> data;
    private final String IMAGE_PATH = "https://image.tmdb.org/t/p/w342";

    public EpisodeTVShowAdapter(List<Episode> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public EpisodeTVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode,parent,false);
        ItemEpisodeBinding binder = ItemEpisodeBinding.bind(view);
        return new EpisodeTVShowViewHolder(binder);

    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeTVShowViewHolder holder, int position) {
        Episode episode = data.get(position);
        holder.episodeName.setText((position +1) +"." + episode.getName());
        holder.episodeOverView.setText(episode.getOverview());
        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(IMAGE_PATH+episode.getStillPath())).transform(new CenterCrop())
                .into(holder.episodePoster);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class EpisodeTVShowViewHolder extends RecyclerView.ViewHolder{
        private ImageView episodePoster;
        private TextView episodeName;
        private TextView episodeOverView;

        public EpisodeTVShowViewHolder(@NonNull ItemEpisodeBinding binder) {
            super(binder.getRoot());
            episodePoster = binder.episodePoster;
            episodeName = binder.episodeName;
            episodeOverView = binder.episodeOverview;
        }
    }
}
