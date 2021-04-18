package com.example.pinayflix.ui.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.pinayflix.R;
import com.example.pinayflix.databinding.DialogDetailsBinding;
import com.example.pinayflix.model.datamodel.trailer.Trailer;
import com.example.pinayflix.model.datamodel.tvshow.TVShow;
import com.example.pinayflix.viewmodel.MainFragmentViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;
import java.util.Objects;

public class TVShowDetailsDialog extends BottomSheetDialogFragment {
    private DialogDetailsBinding binder;
    private ImageView backDropIV;
    private TextView tvShowTitle;
    private TextView tvShowYear;
    private AppCompatButton playBtn;
    private AppCompatButton detailsBtn;
    private ImageButton closeBtn;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer player;

    private MainFragmentViewModel mainFragmentViewModel;
    private TVShow tvShow;
    private static final String TAG = "TVShowDetailsDialog";
    private final String IMAGE_PATH = "https://image.tmdb.org/t/p/w1280";

    public TVShowDetailsDialog(TVShow selectedTvShow) {
        this.tvShow = selectedTvShow;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binder = DialogDetailsBinding.inflate(inflater, container, false);
        return binder.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainFragmentViewModel = new ViewModelProvider(getParentFragment()).get(MainFragmentViewModel.class);
        backDropIV = binder.backDropIV;
        tvShowTitle = binder.movieTitle;
        tvShowYear = binder.movieYear;
        playBtn = binder.playTrailerBtn;
        detailsBtn = binder.movieDetailsBtn;
        closeBtn = binder.closeBtn;

        youTubePlayerView = binder.youtubePlayerView;

        mainFragmentViewModel.enableBtn(false);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                player = youTubePlayer;
                mainFragmentViewModel.enableBtn(true);


            }
        });
        mainFragmentViewModel.getEnablePlayBtn().observe(getViewLifecycleOwner(), value -> {
            enableButton(value);
            if (value)
                playBtn.setOnClickListener(btn -> {
                    playTrailer();
                });

        });

        detailsBtn.setOnClickListener(btn -> {
            mainFragmentViewModel.requestTvShowDetails(tvShow.getId());
            dismiss();
        });

        closeBtn.setOnClickListener(btn -> {
            dismiss();
        });

        tvShowTitle.setText(tvShow.getName());
        tvShowYear.setText(tvShow.getFirstAirDate().substring(0, 4));
        RequestOptions requestOptions = new RequestOptions();
        GranularRoundedCorners roundedCorners = new GranularRoundedCorners(48, 48, 0, 0);
        requestOptions = requestOptions.transforms(new CenterCrop(), roundedCorners);
        Glide.with(requireContext()).load(IMAGE_PATH + tvShow.getBackDropPath()).apply(requestOptions)
                .into(backDropIV);

    }

    private void enableButton(boolean isEnable) {
        if (isEnable) {
            playBtn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_button_enable));
            playBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.netflix_black));
            playBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(requireContext(), R.drawable.ic_play), null, null, null);
        } else {
            playBtn.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_button_disabled));
            playBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.netflix_white));
            playBtn.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(requireContext(), R.drawable.ic_play_white), null, null, null);

        }
        playBtn.setEnabled(isEnable);


    }

    private void playTrailer() {
        if (player == null) return;

        //Show Youtube Player View
        youTubePlayerView.setVisibility(View.VISIBLE);


        mainFragmentViewModel.requestTvShowTrailer(tvShow.getId());
        mainFragmentViewModel.getTvShowTrailer().observe(getViewLifecycleOwner(), trailers -> {
            if(trailers == null || trailers.size() ==0) return;

            String videoId = getTrailerYoutubeKey(trailers);
            player.loadVideo(videoId, 0);

        });
    }

    private String getTrailerYoutubeKey(List<Trailer> trailers) {
        for (Trailer trailer : trailers) {
            if (trailer.getType().equals("Trailer"))
                return trailer.getKey();

        }
        return trailers.get(0).getKey();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        youTubePlayerView.release();
    }
}
