package com.example.pinayflix.adapter.recyclerview.tvshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinayflix.R;
import com.example.pinayflix.databinding.ItemEpisodeBinding;
import com.example.pinayflix.model.datamodel.tvshow.Episode;

import java.util.ArrayList;

public class EpisodeTVShowAdapter extends RecyclerView.Adapter<EpisodeTVShowAdapter.EpisodeTVShowViewHolder> {
    private ArrayList<Episode> data;

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
