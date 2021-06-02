package com.example.pinayflix.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
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
import com.example.pinayflix.R;
import com.example.pinayflix.adapter.recyclerview.tvshow.EpisodeTVShowAdapter;
import com.example.pinayflix.adapter.recyclerview.tvshow.SimilarTVShowAdapter;
import com.example.pinayflix.adapter.recyclerview.tvshow.TVShowReviewsAdapter;
import com.example.pinayflix.databinding.LayoutDetailsTvshowBinding;
import com.example.pinayflix.model.datamodel.review.Review;
import com.example.pinayflix.model.datamodel.tvshow.Episode;
import com.example.pinayflix.model.datamodel.tvshow.TVShow;
import com.example.pinayflix.ui.custom.ExpandableTextView;
import com.example.pinayflix.ui.custom.FadingImageView;
import com.example.pinayflix.ui.dialogs.ProgressBarDialog;
import com.example.pinayflix.ui.dialogs.SeasonListDialog;
import com.example.pinayflix.utitlies.Utils;
import com.example.pinayflix.viewmodel.TVShowDetailsFragmentViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import jp.wasabeef.glide.transformations.BlurTransformation;

@AndroidEntryPoint
public class TVShowDetailsFragment extends Fragment {
    public static final String DETAILS_KEY = "Details";
    private LayoutDetailsTvshowBinding binder;
    private ImageView tvShowPoster;
    private FadingImageView backDrop;
    private RatingBar ratingBar;
    private ExpandableTextView overViewTv;
    private TVShowDetailsFragmentViewModel viewModel;
    private TVShowReviewsAdapter reviewsAdapter;
    private TabLayout tabLayout;
    private AppCompatButton seasonBtn;
    private RecyclerView rv;
    private EpisodeTVShowAdapter episodesAdapter;
    private SimilarTVShowAdapter similarAdapter;

    private AppCompatButton addToListBtn;
    private AppCompatButton rateTvShowBtn;
    private AppCompatButton shareTvShowBtn;


    private static final String TAG = "TVShowDetailsFragment";

    @Inject
    RequestManager requestManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = LayoutDetailsTvshowBinding.inflate(inflater, container, false);
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

        addToListBtn = binder.myListBtn;
        rateTvShowBtn = binder.rateBtn;
        shareTvShowBtn = binder.shareBtn;


        rv = binder.episodesRv;


        tabLayout.addTab(tabLayout.newTab().setText("Episode"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));
        tabLayout.addTab(tabLayout.newTab().setText("More like this"));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
                    if (viewModel.getSimilarTVShows().getValue() == null)
                        viewModel.requestSimilarTvShows();
                    else
                        setSimilarTVShowViewModel();
                } else if (tab.getPosition() == 1) {
                    if (viewModel.getTvShowReviews().getValue() == null)
                        viewModel.requestTvShowReviews();
                    else
                        setReviewsViewModel();
                } else if (tab.getPosition() == 0)
                    if (viewModel.getSimilarTVShows().getValue() == null)
                        viewModel.requestSeason(1);
                    else
                        setEpisodeViewModel();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        addToListBtn.setOnClickListener(btn -> {
            TVShow tvShow = viewModel.getTvShowDetails().getValue();
            if (tvShow == null) return;
            if (viewModel.getTvShowExists().getValue() == null) return;
            boolean doesItemExists = viewModel.getTvShowExists().getValue();
            if (doesItemExists) {
                viewModel.removeTvShowFromList(tvShow);
                Toast.makeText(getContext(), "Removed from My List", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.addTvShowToList(tvShow);
                Toast.makeText(getContext(), "Added to My List", Toast.LENGTH_SHORT).show();
            }

        });

        seasonBtn.setOnClickListener(btn -> {
            showSeasonList();
        });

        viewModel.getTvShowDetails().observe(getViewLifecycleOwner(), tvShowDetails -> {
            if (tvShowDetails.getNumOfSeasons() <= 1)
                seasonBtn.setVisibility(View.GONE);

            setTvShowDetailsToUi(tvShowDetails);
            showHideProgressBar(false);


        });
        viewModel.getSelectedSeason().observe(getViewLifecycleOwner(), value -> {
            seasonBtn.setText("Season " + value);

        });
        viewModel.getTvShowExists().observe(getViewLifecycleOwner(), doesExists -> {
            Drawable drawable;
            if (doesExists)
                drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_check);
            else
                drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_add);


            addToListBtn.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            addToListBtn.setText("My List");


        });
        setEpisodeViewModel();
        setSimilarTVShowViewModel();
        setReviewsViewModel();

        showHideProgressBar(true);
    }

    private void setTvShowDetailsToUi(TVShow tvShow) {
        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        requestManager.
                load(Utils.HIGHLIGHTED_IMAGE_PATH + tvShow.getBackDropPath())
                .transition(DrawableTransitionOptions.with(factory))
                .apply(new RequestOptions().transform(
                        new BlurTransformation(20, 2)))
                .into(backDrop);

        requestManager.load(Utils.POSTER_PATH + tvShow.getPosterPath())
                .apply(new RequestOptions().transform(new RoundedCorners(16)))
                .into(tvShowPoster);

        ratingBar.setRating((float) tvShow.getVoteAverage() / 2);
        overViewTv.setText(tvShow.getOverview());
    }

    private void setSimilarTVShowViewModel() {
        viewModel.getSimilarTVShows().observe(getViewLifecycleOwner(), tvShows -> {
            if (tvShows == null) return;
            for (TVShow tvShow : tvShows) {
                Log.d(TAG, "Similar TV Show name " + tvShow.getTitle());

            }

            setSimilarTvShows(tvShows);
        });
    }

    private void setEpisodeViewModel() {
        viewModel.getEpisodes().observe(getViewLifecycleOwner(), episodes -> {
            if (episodes == null) return;

            setEpisodesRV(episodes);

        });

    }

    private void setReviewsViewModel() {
        viewModel.getTvShowReviews().observe(getViewLifecycleOwner(), reviews -> {
            if (reviews == null || reviews.size() == 0) return;
            setReviewsRV(reviews);
        });


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

    private void setEpisodesRV(List<Episode> episodes) {
        seasonBtn.setVisibility(View.VISIBLE);
        episodesAdapter = new EpisodeTVShowAdapter(episodes, requestManager);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setAdapter(episodesAdapter);
    }

    private void setReviewsRV(List<Review> reviews) {
        seasonBtn.setVisibility(View.GONE);
        reviewsAdapter = new TVShowReviewsAdapter(reviews);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setAdapter(reviewsAdapter);


    }

    private void setSimilarTvShows(List<TVShow> tvShows) {
        seasonBtn.setVisibility(View.GONE);

        similarAdapter = new SimilarTVShowAdapter(tvShows, requestManager);
        rv.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        rv.setAdapter(similarAdapter);


    }
}
