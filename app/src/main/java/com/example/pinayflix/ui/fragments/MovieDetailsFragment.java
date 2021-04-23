package com.example.pinayflix.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.pinayflix.adapter.recyclerview.movie.MovieReviewAdapter;
import com.example.pinayflix.adapter.recyclerview.movie.SimilarMovieAdapter;
import com.example.pinayflix.databinding.LayoutDetailsMovieBinding;
import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.ui.custom.ExpandableTextView;
import com.example.pinayflix.ui.custom.FadingImageView;
import com.example.pinayflix.utitlies.Utils;
import com.example.pinayflix.viewmodel.MovieDetailsFragmentViewModel;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import jp.wasabeef.glide.transformations.BlurTransformation;

@AndroidEntryPoint
public class MovieDetailsFragment extends Fragment {
    private LayoutDetailsMovieBinding binder;
    private ImageView moviePoster;
    private FadingImageView backDrop;
    private RatingBar ratingBar;
    private ExpandableTextView overViewTv;


    private TabLayout tabLayout;
    private TextView noReviewsMsg;
    private RecyclerView movieDetailsRV;
    private SimilarMovieAdapter similarMovieAdapter;
    public static final String DETAILS_KEY = "Details";
    private MovieDetailsFragmentViewModel viewModel;
    private MovieReviewAdapter reviewAdapter;
    @Inject
    RequestManager requestManager;
    private String TAG = "MovieDetailsFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = LayoutDetailsMovieBinding.inflate(inflater, container, false);
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this)
                .get(MovieDetailsFragmentViewModel.class);

        moviePoster = binder.moviePoster;
        backDrop = binder.backDropIV;
        backDrop.setFadeBottom(true);
        ratingBar = binder.ratingBar;
        overViewTv = binder.overViewTv;
        noReviewsMsg = binder.noReviewsMsg;
        movieDetailsRV = binder.movieDetailsRV;
        tabLayout = binder.tabLayout;


        tabLayout.addTab(tabLayout.newTab().setText("More like this"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0)
                    viewModel.requestSimilarMovies();
                else if (tab.getPosition() == 1)
                    viewModel.requestReviews();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewModel.getMovieDetails().observe(getViewLifecycleOwner(), movieDetails -> {
            if (movieDetails != null)
                setMovieDetailsToUi(movieDetails);

        });
        viewModel.getReviews().observe(getViewLifecycleOwner(), reviews -> {
            if (reviews == null || reviews.size() == 0) return;
            reviewAdapter = new MovieReviewAdapter(reviews);
            movieDetailsRV.setLayoutManager(new LinearLayoutManager(requireContext()));
            movieDetailsRV.setAdapter(reviewAdapter);


        });

        viewModel.getSimilarMovies().observe(getViewLifecycleOwner(), similarMovies -> {
            similarMovieAdapter = new SimilarMovieAdapter(similarMovies, requestManager);
            movieDetailsRV.setLayoutManager(new GridLayoutManager(requireContext(), 3));
            movieDetailsRV.setAdapter(similarMovieAdapter);
        });

    }

    private void setMovieDetailsToUi(Movie movie) {
        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();

        requestManager.load(Utils.BACKDROP_PATH + movie.getBackdropPath())
                .transition(DrawableTransitionOptions.with(factory))
                .apply(new RequestOptions().transform(
                        new BlurTransformation(20, 2)))
                .into(backDrop);

        requestManager.load(Utils.POSTER_PATH + movie.getPosterPath())
                .apply(new RequestOptions().transform(new RoundedCorners(16)))
                .into(moviePoster);

        ratingBar.setRating((float) movie.getVoteAverage() / 2);
        overViewTv.setText(movie.getOverview());

    }
}
