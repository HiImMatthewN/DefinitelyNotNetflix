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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.pinayflix.adapter.recyclerview.tvshow.SimilarMovieAdapter;
import com.example.pinayflix.adapter.viewpager.ReviewVPAdapter;
import com.example.pinayflix.databinding.LayoutDetailsBinding;
import com.example.pinayflix.model.datamodel.movie.MovieDetails;
import com.example.pinayflix.ui.custom.AutoScrollViewPager;
import com.example.pinayflix.ui.custom.ExpandableTextView;
import com.example.pinayflix.ui.custom.FadingImageView;
import com.example.pinayflix.viewmodel.MovieDetailsFragmentViewModel;
import com.google.android.material.tabs.TabLayout;

import dagger.hilt.android.AndroidEntryPoint;
import jp.wasabeef.glide.transformations.BlurTransformation;

@AndroidEntryPoint
public class MovieDetailsFragment extends Fragment {
    private LayoutDetailsBinding binder;
    private ImageView moviePoster;
    private FadingImageView backDrop;
    private RatingBar ratingBar;
    private ExpandableTextView overViewTv;
    private AutoScrollViewPager reviewsVP;
    private ReviewVPAdapter reviewVPAdapter;
    private TabLayout tabLayout;
    private TextView noReviewsMsg;
    private RecyclerView movieDetailsRV;
    private SimilarMovieAdapter similarMovieAdapter;
    public static final String DETAILS_KEY = "Details";
    private final String BACKDROP_IMAGE_PATH = "https://image.tmdb.org/t/p/w500";
    private final String POSTER_IMAGE_PATH = "https://image.tmdb.org/t/p/w342";
    private MovieDetailsFragmentViewModel movieDetailsFragmentViewModel;


    private String TAG = "MovieDetailsFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = LayoutDetailsBinding.inflate(inflater, container, false);
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieDetailsFragmentViewModel = new ViewModelProvider(this)
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




        //AutoScrollViewPager
//        reviewsVP = binder.reviewsVP;
//        reviewsVP.setCycle(true);
//        reviewsVP.setPagingEnabled(false);
//        reviewsVP.setInterval(10000);
//        reviewsVP.startAutoScroll();
//        reviewsVP.setOffscreenPageLimit(1);


        movieDetailsFragmentViewModel.getMovieDetails().observe(getViewLifecycleOwner(), movieDetails -> {
            if (movieDetails != null)
                setMovieDetailsToUi(movieDetails);

        });
        movieDetailsFragmentViewModel.getReviewsCount().observe(getViewLifecycleOwner(), reviewCount -> {
//            Log.d(TAG, " Review Count" + reviewCount);
//
//            if (reviewCount == 0) {
//                noReviewsMsg.setVisibility(View.VISIBLE);
//                Log.d(TAG, "Showing no review message ");
//            } else {
//                reviewVPAdapter = new ReviewVPAdapter(getChildFragmentManager(), 1, reviewCount);
//                reviewsVP.setAdapter(reviewVPAdapter);
//            }


        });

        movieDetailsFragmentViewModel.getSimilarMovies().observe(getViewLifecycleOwner(),similarMovies ->{
                similarMovieAdapter = new SimilarMovieAdapter(similarMovies);
                movieDetailsRV.setAdapter(similarMovieAdapter);
        });

//        reviewsVP.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP)
//                    reviewsVP.startAutoScroll();
//                else
//                    reviewsVP.stopAutoScroll();
//
//                return true;
//            }
//        });


    }

    private void setMovieDetailsToUi(MovieDetails movieDetails) {
        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        Glide.with(requireContext()).
                load(BACKDROP_IMAGE_PATH + movieDetails.getBackdropPath())
                .transition(DrawableTransitionOptions.with(factory))
                .apply(new RequestOptions().transform(new CenterCrop(),
                        new BlurTransformation(20, 2)))
                .into(backDrop);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        Glide.with(requireContext()).load(POSTER_IMAGE_PATH + movieDetails.getPosterPath())
                .apply(requestOptions)
                .into(moviePoster);

        ratingBar.setRating((float) movieDetails.getVoteAverage() / 2);
        overViewTv.setText(movieDetails.getOverview());

    }
}
