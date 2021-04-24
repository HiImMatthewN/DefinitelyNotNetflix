package com.example.pinayflix.adapter.recyclerview.tvshow;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.pinayflix.R;
import com.example.pinayflix.callback.OnTVShowRequest;
import com.example.pinayflix.databinding.ItemChildBinding;
import com.example.pinayflix.model.datamodel.tvshow.TVShow;
import com.example.pinayflix.model.uimodel.TVShowCategoryModel;
import com.example.pinayflix.utitlies.Utils;

import java.util.List;

public class ChildTVShowAdapter extends RecyclerView.Adapter<ChildTVShowAdapter.ChildTVShowViewHolder> {
    private List<TVShow> data;
    private String TAG = "ChildTVShowAdapter";
    private OnTVShowRequest callback;
    private RequestManager requestManager;

    public ChildTVShowAdapter(TVShowCategoryModel categoryModel, OnTVShowRequest callback, RequestManager requestManager) {
        this.callback = callback;
        this.data = categoryModel.getTVShows();
        this.requestManager = requestManager;

        notifyDataSetChanged();

    }

    public void insertData(List<TVShow> tvShows) {
        Log.d(TAG, "insertData: new TV Shows added ");
        data.addAll(tvShows);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ChildTVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child, parent, false);
        ItemChildBinding binder = ItemChildBinding.bind(view);
        return new ChildTVShowViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildTVShowViewHolder holder, int position) {

        TVShow tvShow = data.get(position);
        ImageView target = holder.posterImageView;

        requestManager
                .load(Uri.parse(Utils.POSTER_PATH + tvShow.getPosterPath()))
                .into(target);
        holder.setOnClick(tvShow);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ChildTVShowViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImageView;

        public ChildTVShowViewHolder(@NonNull ItemChildBinding binder) {
            super(binder.getRoot());
            posterImageView = binder.moviePosterIv;
        }

        public void setOnClick(TVShow tvShow) {
            posterImageView.setOnClickListener(btn -> {
                callback.onTVShowSelect(tvShow);
            });
            posterImageView.setOnLongClickListener(btn -> {
                Toast.makeText(itemView.getContext(), tvShow.getName(), Toast.LENGTH_SHORT).show();
                return true;
            });
        }
    }

}
