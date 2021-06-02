package com.example.pinayflix.adapter.recyclerview.movie;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.pinayflix.R;
import com.example.pinayflix.callback.OnMovieRequest;
import com.example.pinayflix.databinding.ItemChildBinding;
import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.model.uimodel.MovieCategoryModel;
import com.example.pinayflix.utitlies.Utils;

import java.util.List;

public class ChildMovieAdapter extends RecyclerView.Adapter<ChildMovieAdapter.ChildMovieViewHolder> {
    private List<Movie> data;
    private String TAG = "ChildMovieAdapter";
    private OnMovieRequest callback;
    private RequestManager requestManager;

    public ChildMovieAdapter(MovieCategoryModel movieCategoryModel, OnMovieRequest callback,RequestManager requestManager) {
        this.callback = callback;
        this.data = movieCategoryModel.getMovies();
        this.requestManager = requestManager;

        notifyDataSetChanged();
    }

    public void insertData(List<Movie> movies) {
        Log.d(TAG, "insertData: new Movies added ");
        data.addAll(movies);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ChildMovieAdapter.ChildMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child, parent, false);
        ItemChildBinding binder = ItemChildBinding.bind(view);
        return new ChildMovieViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildMovieAdapter.ChildMovieViewHolder holder, int position) {

        Movie movie = data.get(position);
        ImageView target = holder.posterImageView;

        Log.d(TAG, "onBindViewHolder: Shimmer");

        requestManager.
                load(Uri.parse(Utils.POSTER_PATH + movie.getPosterPath())).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                return true;
            }
        })
                .apply(new RequestOptions().transform(new RoundedCorners(16)))
                .into(target);
        holder.setOnClick(movie);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ChildMovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImageView;
        public ChildMovieViewHolder(@NonNull ItemChildBinding binder) {
            super(binder.getRoot());
            posterImageView = binder.posterIv;
        }

        public void setOnClick(Movie movie) {
            posterImageView.setOnClickListener(btn -> {
                callback.onMovieSelected(movie);

            });
            posterImageView.setOnLongClickListener(btn -> {
                Toast.makeText(itemView.getContext(), movie.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            });
        }
    }

}
