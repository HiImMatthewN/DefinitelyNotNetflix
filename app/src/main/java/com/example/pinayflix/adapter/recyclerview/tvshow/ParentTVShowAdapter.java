package com.example.pinayflix.adapter.recyclerview.tvshow;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.RequestManager;
import com.example.pinayflix.DataGenre;
import com.example.pinayflix.R;
import com.example.pinayflix.callback.OnTVShowRequest;
import com.example.pinayflix.databinding.ItemCategoryBinding;
import com.example.pinayflix.model.datamodel.tvshow.TVShow;
import com.example.pinayflix.model.uimodel.TVShowCategoryModel;

import java.util.ArrayList;

public class ParentTVShowAdapter extends RecyclerView.Adapter<ParentTVShowAdapter.ParentTVViewHolder> implements OnTVShowRequest {
    private ArrayList<TVShowCategoryModel> data;
    private String TAG = "ParentTVShowdapter";
    private MutableLiveData<DataGenre> dataGenreMutableLiveData;
    private MutableLiveData<TVShow> tvShowSelectedLiveData;
    private DataGenre requestingDataGenre;
    private RequestManager requestManager;
    public ParentTVShowAdapter(RequestManager requestManager) {
        dataGenreMutableLiveData = new MutableLiveData<>();
        tvShowSelectedLiveData = new MutableLiveData<>();
        this.requestManager = requestManager;

        data = new ArrayList<>();
        notifyDataSetChanged();

    }

    public void insertData(TVShowCategoryModel categoryModel) {
            data.add(categoryModel);
            notifyItemInserted(data.indexOf(categoryModel));
    }

    public int getAdapterPosition(){

        for (int i = 0;i<data.size();i++){
            if(data.get(i).getCategoryName().equals(requestingDataGenre)){
                Log.d(TAG, "getAdapterPosition: Requesing Classification " + requestingDataGenre);
                return i;

            }
        }
        return 0;
    }
    @Override
    public void onTVShowSelect(TVShow tvShow) {
        tvShowSelectedLiveData.postValue(tvShow);
    }

    @NonNull
    @Override
    public ParentTVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
       ItemCategoryBinding binder =  ItemCategoryBinding.bind(view);

        //Settingn item to 30 percent height
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.250);
        view.setLayoutParams(layoutParams);
        return new ParentTVViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentTVViewHolder holder, int position) {
        TVShowCategoryModel categoryModel = data.get(position);
        ChildTVShowAdapter adapter = new ChildTVShowAdapter(categoryModel, this,requestManager);

        RecyclerView tvShowsRV = holder.tvShows;
        TextView category = holder.category;
        SwipeRefreshLayout swipeRefreshLayout = holder.swipeRefreshLayout;
        Resources res = holder.itemView.getResources();
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(res.getColor(R.color.netflix_white));
        swipeRefreshLayout.setColorSchemeColors(res.getColor(R.color.netflix_red));



        category.setText(categoryModel.getCategoryName().toString());
        tvShowsRV.setAdapter(adapter);
        holder.setRVScrollListener(categoryModel);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public LiveData<DataGenre> onRequestLiveData() {
        return dataGenreMutableLiveData;
    }
    public LiveData<TVShow> getSelectedTvShow(){
        return tvShowSelectedLiveData;
    }

    class ParentTVViewHolder extends RecyclerView.ViewHolder {
        private TextView category;
        private RecyclerView tvShows;
        private SwipeRefreshLayout swipeRefreshLayout;

        public ParentTVViewHolder(@NonNull ItemCategoryBinding binder) {
            super(binder.getRoot());
            category = binder.category;
            tvShows = binder.dataList;
            swipeRefreshLayout = binder.swipeRefreshLayout;
        }


        public void setRVScrollListener(TVShowCategoryModel categoryModel) {
            tvShows.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    LinearLayoutManager layoutManager = ((LinearLayoutManager)tvShows.getLayoutManager());
                    if(layoutManager == null ) return;
                    int pos = layoutManager.findLastCompletelyVisibleItemPosition();
                    int numItems = tvShows.getAdapter().getItemCount();
                    if (pos +1 >= numItems){
                        requestingDataGenre = categoryModel.getCategoryName();
                        dataGenreMutableLiveData.postValue(categoryModel.getCategoryName());
                        swipeRefreshLayout.setRefreshing(true);
                    }

                }
            });

        }

    }
}
