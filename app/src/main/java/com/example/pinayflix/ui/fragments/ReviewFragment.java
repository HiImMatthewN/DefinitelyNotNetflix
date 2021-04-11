package com.example.pinayflix.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pinayflix.R;
import com.example.pinayflix.databinding.LayoutReviewBinding;
import com.example.pinayflix.model.datamodel.review.Review;
import com.example.pinayflix.ui.custom.ExpandableTextView;
import com.example.pinayflix.viewmodel.MovieDetailsFragmentViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewFragment extends Fragment {
    private LayoutReviewBinding binder;
    private CircleImageView avatarIv;
    private TextView authorName;
    private RatingBar authorRating;
    private ExpandableTextView content;
    private int fragmentPosition = 0;
    private MovieDetailsFragmentViewModel movieDetailsFragmentViewModel;
    private final String IMAGE_PATH = "https://image.tmdb.org/t/p/w342";

    private String TAG = "ReviewFragment";

    public ReviewFragment(int pos) {
        this.fragmentPosition = pos;


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = LayoutReviewBinding.inflate(inflater, container, false);
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieDetailsFragmentViewModel = new ViewModelProvider(getParentFragment()).get(MovieDetailsFragmentViewModel.class);

        avatarIv = binder.avatarIv;
        authorName = binder.authorName;
        authorRating = binder.authorRating;
        content = binder.content;

        Review review = movieDetailsFragmentViewModel.getReviewFromPos(fragmentPosition);
        authorName.setText(review.getAuthor());
        authorRating.setRating((float) review.getAuthorDetails().getRating()/2);
        content.setText("\"" + review.getContent() + "\"");
        Glide.with(requireContext()).load(IMAGE_PATH + review.getAuthorDetails().getAvatarPath()).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Log.d(TAG, "onLoadFailed: Loading image error " + e.getMessage());

                return true;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        })
                .into(avatarIv).onLoadFailed(ContextCompat.getDrawable(requireContext(), R.drawable.ic_person));


    }
}
