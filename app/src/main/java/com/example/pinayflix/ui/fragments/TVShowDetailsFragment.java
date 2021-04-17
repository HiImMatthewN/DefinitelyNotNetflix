package com.example.pinayflix.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.pinayflix.databinding.LayoutTvShowDetailsBinding;
import com.example.pinayflix.model.datamodel.tvshow.Episode;
import com.example.pinayflix.model.datamodel.tvshow.TVShowDetails;
import com.example.pinayflix.ui.custom.ExpandableTextView;
import com.example.pinayflix.ui.custom.FadingImageView;
import com.example.pinayflix.ui.dialogs.ProgressBarDialog;
import com.example.pinayflix.ui.dialogs.SeasonListDialog;
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
    private AppCompatButton seasonBtn;
    private RecyclerView episodeRv;
    private static final String TAG = "TVShowDetailsFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = LayoutTvShowDetailsBinding.inflate(inflater, container, false);
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
        seasonBtn = binder.seasonBtn;

        episodeRv = binder.episodesRv;


        tabLayout.addTab(tabLayout.newTab().setText("Episode"));
        tabLayout.addTab(tabLayout.newTab().setText("More like this"));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        seasonBtn.setOnClickListener(btn -> {
            showSeasonList();
        });

        viewModel.getTvShowDetails().observe(getViewLifecycleOwner(), tvShowDetails -> {
            if (tvShowDetails.getNumOfSeasons() <= 1)
                seasonBtn.setVisibility(View.GONE);

            if (tvShowDetails == null) return;
            setTvShowDetailsToUi(tvShowDetails);

        });
        viewModel.getEpisodes().observe(getViewLifecycleOwner(),episodes -> {
            for (Episode episode : episodes){
//                Log.d(TAG, "Episode Name " + episode.getName() + " Episode no " + episode.getEpisodeNumber());
            }
            showHideProgressBar(false);

        });
        viewModel.getEpisodeRunTime().observe(getViewLifecycleOwner(), runTimes ->{
            for(Integer integer : runTimes){

            }
        });
        showHideProgressBar(true);
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

    private void showSeasonList() {
        SeasonListDialog dialog = new SeasonListDialog();
        dialog.show(getChildFragmentManager(), "Show Season List Dialog");
    }

    private ProgressBarDialog progressBarDialog;

    private void showHideProgressBar(boolean value) {
        if (value) {
            if (progressBarDialog == null)
                progressBarDialog = new ProgressBarDialog();
            progressBarDialog.show(getChildFragmentManager(), "Progress Bar Dialog");
        } else {
            if (progressBarDialog != null) {
                progressBarDialog.dismiss();
                progressBarDialog = null;

            }


        }


    }
}
