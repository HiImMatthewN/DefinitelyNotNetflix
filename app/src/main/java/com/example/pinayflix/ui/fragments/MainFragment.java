package com.example.pinayflix.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.RequestManager;
import com.example.pinayflix.DataClassification;
import com.example.pinayflix.DataGenre;
import com.example.pinayflix.R;
import com.example.pinayflix.adapter.recyclerview.movie.ChildMovieAdapter;
import com.example.pinayflix.adapter.recyclerview.movie.HighlightedMovieAdapter;
import com.example.pinayflix.adapter.recyclerview.movie.ParentMovieAdapter;
import com.example.pinayflix.adapter.recyclerview.tvshow.ChildTVShowAdapter;
import com.example.pinayflix.adapter.recyclerview.tvshow.HighlightedTVShowAdapter;
import com.example.pinayflix.adapter.recyclerview.tvshow.ParentTVShowAdapter;
import com.example.pinayflix.databinding.FragmentMainBinding;
import com.example.pinayflix.databinding.LayoutContentBinding;
import com.example.pinayflix.model.uimodel.MovieCategoryModel;
import com.example.pinayflix.model.uimodel.TVShowCategoryModel;
import com.example.pinayflix.ui.custom.SpeedyLinearLayoutManager;
import com.example.pinayflix.ui.dialogs.MovieDetailsDialog;
import com.example.pinayflix.ui.dialogs.TVShowDetailsDialog;
import com.example.pinayflix.viewmodel.MainFragmentViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends Fragment {
    private FragmentMainBinding binder;
    private MainFragmentViewModel mainFragmentViewModel;
    private RecyclerView parentRv;

    private ConcatAdapter concatAdapter;
    private LayoutContentBinding contentBinding;
    private CollapsingToolbarLayout toolbar;


    //Adapters
    private ParentMovieAdapter movieParentAdapter;
    private HighlightedMovieAdapter highlightedMovieAdapter;
    private ParentTVShowAdapter tvShowParentAdapter;
    private HighlightedTVShowAdapter highlightedTVShowAdapter;


    //Movie Category
    private TextView movieCategoryTV;
    private TextView tvListCategoryTV;
    private TextView myListTV;

    private String TAG = "MainFragment";

    private NavController navController;

    @Inject
    RequestManager requestManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = FragmentMainBinding.inflate(inflater, container, false);
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
        navController = Navigation.findNavController(view);

        movieCategoryTV = binder.movieCategoryTV;
        tvListCategoryTV = binder.tvShowCategoryTV;
        contentBinding = binder.content;

        myListTV = binder.myListTV;
        parentRv = contentBinding.parentRv;
        parentRv.setLayoutManager(new SpeedyLinearLayoutManager(requireContext()));
        toolbar = binder.toolbar;


        createMovieRVAdapter();


        ViewCompat.setOnApplyWindowInsetsListener(toolbar, (v, insets) -> {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            params.topMargin = insets.getSystemWindowInsetTop();
            return insets.consumeSystemWindowInsets();
        });

        movieCategoryTV.setOnClickListener(btn -> {
            handleCategoryOnClick(btn.getId());
            mainFragmentViewModel.requestData(DataClassification.MOVIE);

        });

        tvListCategoryTV.setOnClickListener(btn -> {
            handleCategoryOnClick(btn.getId());
            mainFragmentViewModel.requestData(DataClassification.TV_SHOW);


        });

        myListTV.setOnClickListener(btn -> {
            handleCategoryOnClick(btn.getId());

        });
        mainFragmentViewModel.getOnDataClassification().observe(getViewLifecycleOwner(), dataClassification -> {
            if (dataClassification == DataClassification.MOVIE){
                createMovieRVAdapter();
                handleCategoryOnClick(R.id.movieCategoryTV);
            }


            else if (dataClassification == DataClassification.TV_SHOW){
                createTVShowAdapter();
                handleCategoryOnClick(R.id.tvShowCategoryTV);
            }
        });


        //Initialize LiveData that will observe if new data
        // is received from repository
        initMovieDataLiveData();
        initTVShowLiveData();

    }

    private void createMovieRVAdapter() {
        highlightedMovieAdapter = new HighlightedMovieAdapter(requestManager);
        movieParentAdapter = new ParentMovieAdapter(requestManager);
        concatAdapter = new ConcatAdapter(highlightedMovieAdapter, movieParentAdapter);
        parentRv.setAdapter(concatAdapter);

        movieParentAdapter.getSelectedMovie().observe(getViewLifecycleOwner(), selectedMovie -> {
                    MovieDetailsDialog dialog = new MovieDetailsDialog(selectedMovie);
                    dialog.show(getChildFragmentManager(), "Movie Details Dialog");

                }
        );
        movieParentAdapter.onRequestOfNewData().observe(getViewLifecycleOwner(), requestedMovie -> {
            mainFragmentViewModel.requestNewMovie(requestedMovie);
        });
        //Handle when user swiped to load new data
        mainFragmentViewModel.getNewRequestedMovie().observe(getViewLifecycleOwner(), requestedMovies -> {
            View adapterView = parentRv.getLayoutManager().findViewByPosition(movieParentAdapter.getAdapterPosition() + 1);
            if (adapterView == null) return;
            RecyclerView selectedRV = adapterView.findViewById(R.id.dataList);
            SwipeRefreshLayout swipeRefreshLayout = adapterView.findViewById(R.id.swipeRefreshLayout);
            new Handler().postDelayed(() -> {
                ChildMovieAdapter childMovieAdapter = (ChildMovieAdapter) selectedRV.getAdapter();
                if (childMovieAdapter != null)
                    childMovieAdapter.insertData(requestedMovies);
            }, 500);
            new Handler().postDelayed(() -> {
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 500);
        });

    }

    private void createTVShowAdapter() {
        highlightedTVShowAdapter = new HighlightedTVShowAdapter(requestManager);
        tvShowParentAdapter = new ParentTVShowAdapter(requestManager);
        concatAdapter = new ConcatAdapter(highlightedTVShowAdapter, tvShowParentAdapter);
        parentRv.setAdapter(concatAdapter);

        tvShowParentAdapter.getSelectedTvShow().observe(getViewLifecycleOwner(),
                selectedTvShow -> {
                    TVShowDetailsDialog dialog = new TVShowDetailsDialog(selectedTvShow);
                    dialog.show(getChildFragmentManager(), "Show TV Show Details");

                });
        tvShowParentAdapter.onRequestLiveData().observe(getViewLifecycleOwner(), dataGenre -> {
            mainFragmentViewModel.requestNewTVShow(dataGenre);

        });
        mainFragmentViewModel.getNewRequestedTvShow().observe(getViewLifecycleOwner(), newTvShows -> {
            View adapterView = parentRv.getLayoutManager().findViewByPosition(tvShowParentAdapter.getAdapterPosition() + 1);
            if (adapterView == null) return;
            RecyclerView selectedRV = adapterView.findViewById(R.id.dataList);
            SwipeRefreshLayout swipeRefreshLayout = adapterView.findViewById(R.id.swipeRefreshLayout);
            new Handler().postDelayed(() -> {
                ChildTVShowAdapter childTVShowAdapter = (ChildTVShowAdapter) selectedRV.getAdapter();
                if (childTVShowAdapter != null)
                    childTVShowAdapter.insertData(newTvShows);
            }, 500);
            new Handler().postDelayed(() -> {
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 500);


        });
    }

    private void initTVShowLiveData() {
        mainFragmentViewModel.getNowPlayingTvShows().observe(getViewLifecycleOwner(), nowPlayingTVShows -> {
//            highlightedTVShowAdapter.insertData(nowPlayingTVShows.get(randomNum));

        });
        mainFragmentViewModel.getHighlightedTvShow().observe(getViewLifecycleOwner(), tvShow -> {
            highlightedTVShowAdapter.insertData(tvShow);
        });
        mainFragmentViewModel.getPopularTvShows().observe(getViewLifecycleOwner(), popularTvShows -> {
            tvShowParentAdapter.insertData(new TVShowCategoryModel(DataGenre.Popular, popularTvShows));
        });
        mainFragmentViewModel.getUpcomingTvShows().observe(getViewLifecycleOwner(), upComingTvShows -> {
            tvShowParentAdapter.insertData(new TVShowCategoryModel(DataGenre.Upcoming, upComingTvShows));

        });
        mainFragmentViewModel.getHorrorTvShows().observe(getViewLifecycleOwner(), horrorTvShows -> {

            tvShowParentAdapter.insertData(new TVShowCategoryModel(DataGenre.Mystery, horrorTvShows));

        });
        mainFragmentViewModel.getRomanceTvShows().observe(getViewLifecycleOwner(), romanceTvShows -> {
            tvShowParentAdapter.insertData(new TVShowCategoryModel(DataGenre.Romance, romanceTvShows));

        });
        mainFragmentViewModel.getDocumentaryTvShows().observe(getViewLifecycleOwner(), documentaryTvShows -> {
            tvShowParentAdapter.insertData(new TVShowCategoryModel(DataGenre.Documentary, documentaryTvShows));

        });
        mainFragmentViewModel.getTvShowDetails().observe(getViewLifecycleOwner(),event ->{
            if(event.isHandled()) return;
            int tvShowId = event.getContentIfNotHandled();
            Bundle bundle = new Bundle();
            bundle.putSerializable(TVShowDetailsFragment.DETAILS_KEY, tvShowId);
            navController.navigate(R.id.action_mainFragment_to_TVShowDetailsFragment,bundle);

        });

    }

    private void initMovieDataLiveData() {


//        mainFragmentViewModel.getNowPlayingMovies().observe(getViewLifecycleOwner(), nowPlayingMovies -> {
//            highlightedMovieAdapter.insertData(nowPlayingMovies.get(randomNum));
//
//        });

        mainFragmentViewModel.getHighlightedMovieLiveData().observe(getViewLifecycleOwner(), movie -> {
            highlightedMovieAdapter.insertData(movie);

        });

        mainFragmentViewModel.getLatestMovie().observe(getViewLifecycleOwner(), latestMovie -> {
//            movieParentAdapter.insertData(new MovieCategoryModel(DataGenre.Latest, latestMovie));


        });

        mainFragmentViewModel.getPopularMovies().observe(getViewLifecycleOwner(), popularMovies -> {
            movieParentAdapter.insertData(new MovieCategoryModel(DataGenre.Popular, popularMovies));

        });
        mainFragmentViewModel.getUpcomingMovies().observe(getViewLifecycleOwner(), upcomingMovies -> {
            movieParentAdapter.insertData(new MovieCategoryModel(DataGenre.Upcoming, upcomingMovies));

        });
        mainFragmentViewModel.getHorrorMovies().observe(getViewLifecycleOwner(), horrorMovies -> {
            movieParentAdapter.insertData(new MovieCategoryModel(DataGenre.Horror, horrorMovies));

        });
        mainFragmentViewModel.getDocumentaries().observe(getViewLifecycleOwner(), documentaries -> {
            movieParentAdapter.insertData(new MovieCategoryModel(DataGenre.Documentary, documentaries));

        });
        mainFragmentViewModel.getRomanceMovies().observe(getViewLifecycleOwner(), romanceMovies -> {
            movieParentAdapter.insertData(new MovieCategoryModel(DataGenre.Romance, romanceMovies));

        });

        mainFragmentViewModel.getMovieDetails().observe(getViewLifecycleOwner(), integerEvent -> {

            if (!integerEvent.isHandled()) {
                int movieId = integerEvent.getContentIfNotHandled();
                Bundle bundle = new Bundle();
                bundle.putSerializable(MovieDetailsFragment.DETAILS_KEY, movieId);
                navController.navigate(R.id.action_mainFragment_to_movieDetailsFragment, bundle);
            }

        });
    }

    private void handleCategoryOnClick(int id) {
        switch (id) {
            case R.id.movieCategoryTV:
                movieCategoryTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.netflix_category_selected));
                movieCategoryTV.setShadowLayer(8, 0.0f, 0.0f, ContextCompat.getColor(requireContext(), R.color.netflix_white));
                tvListCategoryTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.netflix_category_not_selected));
                tvListCategoryTV.setShadowLayer(0, 0.0f, 0.0f, ContextCompat.getColor(requireContext(), R.color.netflix_white));
                myListTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.netflix_category_not_selected));
                myListTV.setShadowLayer(0, 0.0f, 0.0f, ContextCompat.getColor(requireContext(), R.color.netflix_white));


                break;
            case R.id.tvShowCategoryTV:
                movieCategoryTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.netflix_category_not_selected));
                movieCategoryTV.setShadowLayer(0, 0.0f, 0.0f, ContextCompat.getColor(requireContext(), R.color.netflix_white));

                tvListCategoryTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.netflix_category_selected));
                tvListCategoryTV.setShadowLayer(8, 0.0f, 0.0f, ContextCompat.getColor(requireContext(), R.color.netflix_white));


                myListTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.netflix_category_not_selected));
                movieCategoryTV.setShadowLayer(0, 0.0f, 0.0f, ContextCompat.getColor(requireContext(), R.color.netflix_white));

                break;
            case R.id.myListTV:
                movieCategoryTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.netflix_category_not_selected));
                movieCategoryTV.setShadowLayer(0, 0.0f, 0.0f, ContextCompat.getColor(requireContext(), R.color.netflix_white));
                tvListCategoryTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.netflix_category_not_selected));
                tvListCategoryTV.setShadowLayer(8, 0.0f, 0.0f, ContextCompat.getColor(requireContext(), R.color.netflix_white));
                myListTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.netflix_category_selected));
                myListTV.setShadowLayer(8, 0.0f, 0.0f, ContextCompat.getColor(requireContext(), R.color.netflix_white));


        }


    }


}
