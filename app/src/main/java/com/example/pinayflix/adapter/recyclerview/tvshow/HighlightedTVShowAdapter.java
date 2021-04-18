package com.example.pinayflix.adapter.recyclerview.tvshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.pinayflix.R;
import com.example.pinayflix.databinding.ItemHighlightedBinding;
import com.example.pinayflix.model.datamodel.tvshow.TVShow;
import com.example.pinayflix.ui.custom.FadingImageView;
import com.example.pinayflix.utitlies.Utils;

public class HighlightedTVShowAdapter extends RecyclerView.Adapter<HighlightedTVShowAdapter.HighlightedTVShowViewHolder> {
    private TVShow highlightedTvShow;
    private final String IMAGE_PATH = "https://image.tmdb.org/t/p/w500";
    private String TAG = "HighlightedTVShowAdapter";
    private RequestManager requestManager;

    public HighlightedTVShowAdapter(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public void insertData(TVShow tvShow) {
        this.highlightedTvShow = tvShow;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HighlightedTVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_highlighted, parent, false);
        ItemHighlightedBinding binder = ItemHighlightedBinding.bind(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * .65);
        view.setLayoutParams(layoutParams);
        return new HighlightedTVShowViewHolder(binder);

    }

    @Override
    public void onBindViewHolder(@NonNull HighlightedTVShowViewHolder holder, int position) {
        if (highlightedTvShow != null) {
            FadingImageView target = holder.posterIv;
            target.setFadingEdgeLength(360);
            target.setFadeBottom(true);

            DrawableCrossFadeFactory factory =
                    new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
            requestManager.load(IMAGE_PATH + highlightedTvShow.getPosterPath().trim())
                    .transition(DrawableTransitionOptions.with(factory))
                    .into(target);

            StringBuilder stringBuilder = new StringBuilder();
            for (Integer id : highlightedTvShow.getGenreIds()) {
                stringBuilder.append(Utils.getGenreNameFromId(id)).append("  ");
            }
            holder.genreTv.setText(stringBuilder.toString());
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class HighlightedTVShowViewHolder extends RecyclerView.ViewHolder {
        private FadingImageView posterIv;
        private TextView genreTv;
        private AppCompatButton addToListBtn;
        private AppCompatButton detailsBtn;

        public HighlightedTVShowViewHolder(@NonNull ItemHighlightedBinding binder) {
            super(binder.getRoot());
            posterIv = binder.postIv;
            genreTv = binder.genreTv;
            addToListBtn = binder.addToMyListBtn;
            detailsBtn = binder.movieDetailsBtn;
        }
    }
}
