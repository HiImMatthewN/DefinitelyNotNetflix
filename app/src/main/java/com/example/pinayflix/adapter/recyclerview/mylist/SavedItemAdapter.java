package com.example.pinayflix.adapter.recyclerview.mylist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.pinayflix.R;
import com.example.pinayflix.databinding.ItemSimilarTvShowsBinding;
import com.example.pinayflix.model.datamodel.SavedItem;
import com.example.pinayflix.utitlies.Utils;

import java.util.ArrayList;
import java.util.List;

public class SavedItemAdapter extends RecyclerView.Adapter<SavedItemAdapter.SavedItemViewHolder> {
    private List<SavedItem> data;
    private RequestManager requestManager;
    public SavedItemAdapter(RequestManager requestManager) {
        this.requestManager = requestManager;
        this.data = new ArrayList<>();
    }
    public void insertData(List<SavedItem> savedItems){
        this.data = savedItems;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SavedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_similar_tv_shows,parent,false);
        ItemSimilarTvShowsBinding binder =ItemSimilarTvShowsBinding.bind(view);
        return new SavedItemViewHolder(binder);

    }

    @Override
    public void onBindViewHolder(@NonNull SavedItemViewHolder holder, int position) {
        SavedItem savedItem = data.get(position);
        requestManager.load(Utils.POSTER_PATH +savedItem.getPosterPath())
                .apply(new RequestOptions().transform(new RoundedCorners(16)))

                .into(holder.posterIv);

        holder.bind(savedItem);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SavedItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterIv;

        public SavedItemViewHolder(@NonNull ItemSimilarTvShowsBinding binder) {
            super(binder.getRoot());
            posterIv = binder.posterIv;
        }
        public void bind(SavedItem savedItem){
            posterIv.setOnLongClickListener(btn ->{
                Toast.makeText(itemView.getContext(),savedItem.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            });
        }
    }
}
