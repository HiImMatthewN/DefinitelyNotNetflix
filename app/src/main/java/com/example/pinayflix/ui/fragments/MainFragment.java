package com.example.pinayflix.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pinayflix.MovieClassification;
import com.example.pinayflix.R;
import com.example.pinayflix.adapter.recyclerview.ChildMovieAdapter;
import com.example.pinayflix.adapter.recyclerview.HighlightedMovieAdapter;
import com.example.pinayflix.adapter.recyclerview.ParentMovieAdapter;
import com.example.pinayflix.databinding.FragmentMainBinding;
import com.example.pinayflix.databinding.LayoutContentBinding;
import com.example.pinayflix.model.datamodel.utilities.Event;
import com.example.pinayflix.model.datamodel.Genre;
import com.example.pinayflix.model.uimodel.MovieCategoryModel;
import com.example.pinayflix.ui.dialogs.MovieDetailsDialog;
import com.example.pinayflix.viewmodel.MainFragmentViewModel;

public class MainFragment extends Fragment {
    private FragmentMainBinding binder;
    private MainFragmentViewModel mainActivityViewModel;
    private RecyclerView parentRv;
    private ParentMovieAdapter adapter;
    private HighlightedMovieAdapter highlightedMovieAdapter;
    private ConcatAdapter concatAdapter;
    private LayoutContentBinding contentBinding;

    private String TAG = "MainFragment";

    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = FragmentMainBinding.inflate(inflater, container, false);
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        contentBinding = binder.content;
        parentRv = contentBinding.parentRv;

        highlightedMovieAdapter = new HighlightedMovieAdapter();
        adapter = new ParentMovieAdapter();
        concatAdapter = new ConcatAdapter(highlightedMovieAdapter, adapter);
        parentRv.setAdapter(concatAdapter);

        mainActivityViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);

        mainActivityViewModel.getNowPlayingMovies().observe(getViewLifecycleOwner(), nowPlayingMovies -> {
            int randomNum = (int) (Math.random() * nowPlayingMovies.size());
            highlightedMovieAdapter.insertData(nowPlayingMovies.get(randomNum));

        });

        mainActivityViewModel.getLatestMovie().observe(getViewLifecycleOwner(), latestMovie -> {
            for (Genre genre : latestMovie.getGenres()) {
                Log.d(TAG, "onViewCreated: Genres" + genre.getName());
            }
            Log.d(TAG, "onViewCreated: Movie Name" + latestMovie.getTitle());
            Log.d(TAG, "onViewCreated: Genre Size" + latestMovie.getGenres().size());


        });

        mainActivityViewModel.getPopularMovies().observe(getViewLifecycleOwner(), popularMovies -> {
            adapter.insertData(new MovieCategoryModel(MovieClassification.Popular, popularMovies));

        });
        mainActivityViewModel.getUpcomingMovies().observe(getViewLifecycleOwner(), upcomingMovies -> {
            adapter.insertData(new MovieCategoryModel(MovieClassification.Upcoming, upcomingMovies));

        });
        mainActivityViewModel.getHorrorMovies().observe(getViewLifecycleOwner(), horrorMovies -> {
            adapter.insertData(new MovieCategoryModel(MovieClassification.Horror, horrorMovies));

        });
        mainActivityViewModel.getDocumentaries().observe(getViewLifecycleOwner(), documentaries -> {
            adapter.insertData(new MovieCategoryModel(MovieClassification.Documentary, documentaries));

        });
        mainActivityViewModel.getRomanceMovies().observe(getViewLifecycleOwner(), romanceMovies -> {
            adapter.insertData(new MovieCategoryModel(MovieClassification.Romance, romanceMovies));

        });
//        mainActivityViewModel.onMovieDetailsSelected().observe(getViewLifecycleOwner(), movieId -> {
//
//        });

        adapter.onRequestLiveData().observe(getViewLifecycleOwner(), requestedMovie -> {
            mainActivityViewModel.requestNewData(requestedMovie);
        });

        mainActivityViewModel.onMovieDetailsSelected().observe(getViewLifecycleOwner(), new Observer<Event<Integer>>() {
            @Override
            public void onChanged(Event<Integer> integerEvent) {
                Log.d(TAG, "onChanged: " + integerEvent.isHandled());

                if (!integerEvent.isHandled()) {
                    int movieId = integerEvent.getContentIfNotHandled();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(MovieDetailsFragment.DETAILS_KEY, movieId);
                    navController.navigate(R.id.action_mainFragment_to_movieDetailsFragment, bundle);
                }
            }
        });
        adapter.getSelectedMovie().observe(getViewLifecycleOwner(), selectedMovie -> {
                    MovieDetailsDialog dialog = new MovieDetailsDialog(selectedMovie);
                    dialog.show(getChildFragmentManager(), "Movie Details Dialog");

                }
        );
        Log.d(TAG, "onViewCreated: ");
        onNewDataAvailable();
//        ViewCompat.setOnApplyWindowInsetsListener(toolbar,(v, insets) -> {
//            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
//            params.topMargin = insets.getSystemWindowInsetTop();
//            return insets.consumeStableInsets();
//        });
    }


    private void onNewDataAvailable() {
        mainActivityViewModel.getNewRequestedMovie().observe(getViewLifecycleOwner(), requestedMovies -> {
            View adapterView = parentRv.getLayoutManager().findViewByPosition(adapter.getAdapterPosition() + 1);
            if(adapterView == null) return;
            RecyclerView selectedRV = adapterView.findViewById(R.id.movieList);
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

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
