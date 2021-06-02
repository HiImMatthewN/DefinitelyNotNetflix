package com.example.pinayflix.adapter.recyclerview.tvshow;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.pinayflix.R;
import com.example.pinayflix.databinding.ItemHighlightedBinding;
import com.example.pinayflix.model.datamodel.tvshow.TVShow;
import com.example.pinayflix.model.datamodel.utilities.Event;
import com.example.pinayflix.ui.custom.FadingImageView;
import com.example.pinayflix.utitlies.Utils;

public class HighlightedTVShowAdapter extends RecyclerView.Adapter<HighlightedTVShowAdapter.HighlightedTVShowViewHolder> {
    private TVShow highlightedTvShow;
    private String TAG = "HighlightedTVShowAdapter";
    private RequestManager requestManager;
    private MutableLiveData<Event<Boolean>> isHighlightedTvShowSelected = new MutableLiveData<>();
    private MutableLiveData<Event<TVShow>> onAddSelect = new MutableLiveData<>();
    private MutableLiveData<Event<TVShow>> onDetailsSelect = new MutableLiveData<>();
    public HighlightedTVShowAdapter(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public void insertData(TVShow tvShow) {
        this.highlightedTvShow = tvShow;
        notifyDataSetChanged();
    }
    public void isSelectedTvShowSaved(boolean value) {
        isHighlightedTvShowSelected.setValue(new Event<>(value));
    }

    public LiveData<Event<TVShow>> getOnAddSelect() {
        return onAddSelect;
    }

    public LiveData<Event<TVShow>> getOnDetailsSelect() {
        return onDetailsSelect;
    }
    @NonNull
    @Override
    public HighlightedTVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_highlighted, parent, false);
        ItemHighlightedBinding binder = ItemHighlightedBinding.bind(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * .65);
        view.setLayoutParams(layoutParams);
        return new HighlightedTVShowViewHolder(binder);

    }

    @Override
    public void onBindViewHolder(@NonNull HighlightedTVShowViewHolder holder, int position) {
        if (highlightedTvShow != null) {
            FadingImageView target = holder.posterIv;
            target.setFadingEdgeLength(360);
            target.setFadeBottom(true);

            DrawableCrossFadeFactory factory =
                    new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
            requestManager.load(Utils.POSTER_PATH + highlightedTvShow.getPosterPath().trim())
                    .transition(DrawableTransitionOptions.with(factory))
                    .into(target);

            StringBuilder stringBuilder = new StringBuilder();
            for (Integer id : highlightedTvShow.getGenreIds()) {
                stringBuilder.append(Utils.getGenreNameFromId(id)).append("  ");
            }
            holder.genreTv.setText(stringBuilder.toString());
            holder.bind();
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class HighlightedTVShowViewHolder extends RecyclerView.ViewHolder {
        private FadingImageView posterIv;
        private TextView genreTv;
        private AppCompatButton addToListBtn;
        private AppCompatButton detailsBtn;

        public HighlightedTVShowViewHolder(@NonNull ItemHighlightedBinding binder) {
            super(binder.getRoot());
            posterIv = binder.postIv;
            genreTv = binder.genreTv;
            addToListBtn = binder.addToMyListBtn;
            detailsBtn = binder.movieDetailsBtn;
        }

        public void bind(){
            isHighlightedTvShowSelected.observeForever(value -> {
                if (value.isHandled()) return;
                boolean b = value.getContentIfNotHandled();
                Drawable drawable;
                if (b)
                    drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_check);
                else
                    drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_add);
                addToListBtn.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                addToListBtn.setText("My List");
            });
            addToListBtn.setOnClickListener(btn ->{
                if(highlightedTvShow == null) return;
                onAddSelect.setValue(new Event<>(highlightedTvShow));
            });
            detailsBtn.setOnClickListener(btn ->{
                if(highlightedTvShow == null) return;
                onDetailsSelect.setValue(new Event<>(highlightedTvShow));
            });
        }
    }
}
