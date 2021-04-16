package com.example.pinayflix.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.pinayflix.databinding.LayoutTvShowDetailsBinding;
import com.example.pinayflix.model.datamodel.tvshow.TVShowDetails;
import com.example.pinayflix.ui.custom.ExpandableTextView;
import com.example.pinayflix.ui.custom.FadingImageView;
import com.example.pinayflix.viewmodel.TVShowDetailsFragmentViewModel;
import com.google.android.material.tabs.TabLayout;

import dagger.hilt.android.AndroidEntryPoint;
import jp.wasabeef.glide.transformations.BlurTransformation;

@AndroidEntryPoint
public class TVShowDetailsFragment extends Fragment {
    public static final String DETAILS_KEY = "Details";
    private final String BACKDROP_IMAGE_PATH = "https://image.tmdb.org/t/p/w500";
    private final String POSTER_IMAGE_PATH = "https://image.tmdb.org/t/p/w342";
    private LayoutTvShowDetailsBinding binder;
    private ImageView tvShowPoster;
    private FadingImageView backDrop;
    private RatingBar ratingBar;
    private ExpandableTextView overViewTv;
    private TVShowDetailsFragmentViewModel viewModel;
    private TabLayout tabLayout;
    private static final String TAG = "TVShowDetailsFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = LayoutTvShowDetailsBinding.inflate(inflater,container,false);
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TVShowDetailsFragmentViewModel.class);
        tvShowPoster = binder.tvShowPoster;
        backDrop = binder.backDropIV;
        ratingBar = binder.ratingBar;
        overViewTv = binder.overViewTv;
        tabLayout = binder.tabLayout;
        backDrop.setFadeBottom(true);


        tabLayout.addTab(tabLayout.newTab().setText("Episode"));
        tabLayout.addTab(tabLayout.newTab().setText("More like this"));

        viewModel.getTvShowDetails().observe(getViewLifecycleOwner(),tvShowDetails -> {
            if(tvShowDetails == null) return;

            setTvShowDetailsToUi(tvShowDetails);

        });

    }
    private void setTvShowDetailsToUi(TVShowDetails tvShowDetails) {
        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        Glide.with(requireContext()).
                load(BACKDROP_IMAGE_PATH + tvShowDetails.getBackDropPath())
                .transition(DrawableTransitionOptions.with(factory))
                .apply(new RequestOptions().transform(new CenterCrop(),
                        new BlurTransformation(20, 2)))
                .into(backDrop);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        Glide.with(requireContext()).load(POSTER_IMAGE_PATH + tvShowDetails.getPosterPath())
                .apply(requestOptions)
                .into(tvShowPoster);

        ratingBar.setRating((float) tvShowDetails.getVoteAverage() / 2);
        overViewTv.setText(tvShowDetails.getOverview());

    }
}
