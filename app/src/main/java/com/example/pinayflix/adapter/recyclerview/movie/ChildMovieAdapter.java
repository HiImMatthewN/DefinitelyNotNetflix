package com.example.pinayflix.adapter.recyclerview.movie;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.pinayflix.R;
import com.example.pinayflix.callback.OnMovieRequest;
import com.example.pinayflix.databinding.ItemChildBinding;
import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.model.uimodel.MovieCategoryModel;

import java.util.List;

public class ChildMovieAdapter extends RecyclerView.Adapter<ChildMovieAdapter.ChildMovieViewHolder> {
    private List<Movie> data;
    private String TAG = "ChildMovieAdapter";
    private final String IMAGE_PATH = "https://image.tmdb.org/t/p/w342";
    private OnMovieRequest callback;
    private RequestManager requestManager;

    public ChildMovieAdapter(MovieCategoryModel movieCategoryModel, OnMovieRequest callback,RequestManager requestManager) {
        this.callback = callback;
        this.data = movieCategoryModel.getMovies();
        notifyDataSetChanged();
        this.requestManager = requestManager;
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
        requestManager.load(Uri.parse(IMAGE_PATH + movie.getPosterPath()))
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
            posterImageView = binder.moviePosterIv;
        }

        public void setOnClick(Movie movie) {
            posterImageView.setOnClickListener(btn -> {
                callback.onMovieSelected(movie);
            });
        }
    }

}
