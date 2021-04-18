package com.example.pinayflix.adapter.recyclerview.movie;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.pinayflix.R;
import com.example.pinayflix.databinding.ItemHighlightedBinding;
import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.ui.custom.FadingImageView;
import com.example.pinayflix.utitlies.Utils;

public class HighlightedMovieAdapter extends RecyclerView.Adapter<HighlightedMovieAdapter.HighlightedMovieViewHolder> {
    private Movie highlightedMovie;
    private final String IMAGE_PATH = "https://image.tmdb.org/t/p/w500";
    private String TAG = "HighlightedMovieAdapter";
    public HighlightedMovieAdapter() {

    }

    public void insertData(Movie movie) {
        this.highlightedMovie = movie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HighlightedMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_highlighted, parent, false);
        ItemHighlightedBinding binder = ItemHighlightedBinding.bind(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * .65);
        view.setLayoutParams(layoutParams);
        return new HighlightedMovieViewHolder(binder);

    }
    public void clear(){
        highlightedMovie = null;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull HighlightedMovieViewHolder holder, int position) {
        if (highlightedMovie != null) {
            FadingImageView target = holder.posterIv;
            target.setFadingEdgeLength(360);
            target.setFadeBottom(true);

            DrawableCrossFadeFactory factory =
                    new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
            Glide.with(holder.itemView.getContext()).load(IMAGE_PATH + highlightedMovie.getPosterPath().trim())
                    .transition(DrawableTransitionOptions.with(factory))
                    .transform(new CenterCrop()).into(target);

            StringBuilder stringBuilder = new StringBuilder();
            Log.d(TAG, "onBindViewHolder: Image path" + IMAGE_PATH + highlightedMovie.getPosterPath());
            for (Integer id : highlightedMovie.getGenreIds()) {
                stringBuilder.append(Utils.getGenreNameFromId(id)).append("  ");
            }
            holder.genreTv.setText(stringBuilder.toString());
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class HighlightedMovieViewHolder extends RecyclerView.ViewHolder {
        private FadingImageView posterIv;
        private TextView genreTv;
        private AppCompatButton addToListBtn;
        private AppCompatButton detailsBtn;
        public HighlightedMovieViewHolder(@NonNull ItemHighlightedBinding binder) {
            super(binder.getRoot());
            posterIv = binder.postIv;
            genreTv = binder.genreTv;
            addToListBtn = binder.addToMyListBtn;
            detailsBtn = binder.movieDetailsBtn;
        }
    }
}
