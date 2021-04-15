package com.example.pinayflix.adapter.recyclerview.movie;

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

import com.example.pinayflix.DataGenre;
import com.example.pinayflix.R;
import com.example.pinayflix.callback.OnMovieRequest;
import com.example.pinayflix.databinding.ItemCategoryBinding;
import com.example.pinayflix.model.datamodel.movie.Movie;
import com.example.pinayflix.model.uimodel.MovieCategoryModel;

import java.util.ArrayList;

public class ParentMovieAdapter extends RecyclerView.Adapter<ParentMovieAdapter.ParentMovieViewHolder> implements OnMovieRequest {
    private ArrayList<MovieCategoryModel> data;
    private String TAG = "ParentMovieAdapter";
    private MutableLiveData<DataGenre> movieClassificationMutableLiveData;
    private MutableLiveData<Movie> movieSelectedLiveData;
    private DataGenre requestingDataGenre;
    public ParentMovieAdapter() {
        movieClassificationMutableLiveData = new MutableLiveData<>();
        movieSelectedLiveData = new MutableLiveData<>();
        data = new ArrayList<>();
        notifyDataSetChanged();

    }

    public void insertData(MovieCategoryModel categoryModel) {
            data.add(categoryModel);
            notifyItemInserted(data.indexOf(categoryModel));
    }
    public void clear(){
        data.clear();
        notifyDataSetChanged();
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
    public void onMovieSelected(Movie movie) {
        movieSelectedLiveData.postValue(movie);
    }

    @NonNull
    @Override
    public ParentMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
       ItemCategoryBinding binder =  ItemCategoryBinding.bind(view);

        //Settingn item to 30 percent height
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.250);
        view.setLayoutParams(layoutParams);
        return new ParentMovieViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentMovieViewHolder holder, int position) {
        MovieCategoryModel categoryModel = data.get(position);
        ChildMovieAdapter adapter = new ChildMovieAdapter(categoryModel, this);

        RecyclerView moviesRV = holder.movies;
        TextView category = holder.category;
        SwipeRefreshLayout swipeRefreshLayout = holder.swipeRefreshLayout;
        Resources res = holder.itemView.getResources();
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(res.getColor(R.color.netflix_white));
        swipeRefreshLayout.setColorSchemeColors(res.getColor(R.color.netflix_red));



        category.setText(categoryModel.getCategoryName().toString());
        moviesRV.setAdapter(adapter);
        holder.setRVScrollListener(categoryModel);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public LiveData<DataGenre> onRequestOfNewData() {
        return movieClassificationMutableLiveData;
    }
    public LiveData<Movie> getSelectedMovie(){
        return movieSelectedLiveData;
    }

    class ParentMovieViewHolder extends RecyclerView.ViewHolder {
        private TextView category;
        private RecyclerView movies;
        private SwipeRefreshLayout swipeRefreshLayout;

        public ParentMovieViewHolder(@NonNull ItemCategoryBinding binder) {
            super(binder.getRoot());
            category = binder.category;
            movies = binder.dataList;
            swipeRefreshLayout = binder.swipeRefreshLayout;
        }


        public void setRVScrollListener(MovieCategoryModel categoryModel) {
            movies.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    LinearLayoutManager layoutManager = ((LinearLayoutManager)movies.getLayoutManager());
                    if(layoutManager == null ) return;
                    int pos = layoutManager.findLastCompletelyVisibleItemPosition();
                    int numItems = movies.getAdapter().getItemCount();
                    if (pos +1 >= numItems){
                        requestingDataGenre = categoryModel.getCategoryName();
                        movieClassificationMutableLiveData.postValue(categoryModel.getCategoryName());
                        swipeRefreshLayout.setRefreshing(true);
                    }

                }
            });

        }

    }
}
