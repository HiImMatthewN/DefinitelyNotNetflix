package com.example.pinayflix.adapter.recyclerview.tvshow;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinayflix.R;
import com.example.pinayflix.callback.OnSeasonSelect;
import com.example.pinayflix.databinding.ItemSeaseonBinding;
import com.example.pinayflix.model.datamodel.tvshow.Season;

import java.util.List;

public class SeasonsListAdapter extends RecyclerView.Adapter<SeasonsListAdapter.SeasonsTVShowViewHolder> {
    private List<Season> data;
    private OnSeasonSelect callback;
    private int selectedSeason;
    private static final String TAG = "SeasonsListAdapter";
    public SeasonsListAdapter(List<Season> data, OnSeasonSelect cb) {
        this.data = data;
        this.callback = cb;
    }
    public void setSelected(int id){
        this.selectedSeason = id;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SeasonsTVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seaseon, parent, false);
        ItemSeaseonBinding binder = ItemSeaseonBinding.bind(view);
        return new SeasonsTVShowViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonsTVShowViewHolder holder, int position) {
        holder.seasonTv.setText(data.get(position).getName());
        holder.bind(Integer.parseInt(data.get(position).getSeasonNumber()));
        if(Integer.parseInt(data.get(position).getSeasonNumber()) == selectedSeason){
            Log.d(TAG, "Season " + data.get(position).getSeasonNumber() + " At position " + position);
            holder.seasonTv.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.netflix_white));
        }else
            holder.seasonTv.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.netflix_category_not_selected));

    }

    @Override
    public int getItemCount() {
        return data.size();

    }

    class SeasonsTVShowViewHolder extends RecyclerView.ViewHolder {
        private TextView seasonTv;

        public SeasonsTVShowViewHolder(@NonNull ItemSeaseonBinding binder) {
            super(binder.getRoot());
            seasonTv = binder.seaseonTv;
        }

        public void bind(int seasonId) {
            seasonTv.setOnClickListener(btn -> {

                callback.onSeasonSelect(seasonId);

            });
        }
    }
}
