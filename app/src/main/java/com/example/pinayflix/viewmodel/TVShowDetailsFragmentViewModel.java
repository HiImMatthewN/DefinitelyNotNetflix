package com.example.pinayflix.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.pinayflix.model.datamodel.SavedItem;
import com.example.pinayflix.model.datamodel.review.Review;
import com.example.pinayflix.model.datamodel.tvshow.Episode;
import com.example.pinayflix.model.datamodel.tvshow.Season;
import com.example.pinayflix.model.datamodel.tvshow.TVShow;
import com.example.pinayflix.repository.SavedItemRepository;
import com.example.pinayflix.repository.TVShowRepository;
import com.example.pinayflix.ui.fragments.TVShowDetailsFragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class TVShowDetailsFragmentViewModel extends ViewModel {
    private TVShowRepository tvShowRepository;
    private SavedItemRepository savedItemRepository;
    private int tvId;
    private MutableLiveData<List<Season>> getSeasonsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Episode>> episodesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Integer>> episodeRunTimeLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> currentSelectedSeason = new MutableLiveData<>();
    private static final String TAG = "TVShowDetailsFragmentVi";

    @Inject
    public TVShowDetailsFragmentViewModel(SavedStateHandle savedStateHandle, TVShowRepository tvShowRepository,SavedItemRepository savedItemRepository) {
        this.tvShowRepository = tvShowRepository;
        this.savedItemRepository = savedItemRepository;
        tvId = savedStateHandle.get(TVShowDetailsFragment.DETAILS_KEY);
        savedItemRepository.checkIfSavedItemExists(tvId);
        requestTvShowDetails(tvId);
        requestSeason(1);
        Log.d(TAG, "TVShowDetailsFragmentViewModel: created");
    }

    private void requestTvShowDetails(int tvId) {
        tvShowRepository.requestTvShowDetails(tvId);

    }

    public void requestSimilarTvShows() {
        tvShowRepository.requestRecos(tvId);
    }

    public void requestSeason(int seasonNum) {
        tvShowRepository.requestTvShowSeason(tvId, seasonNum);
        currentSelectedSeason.postValue(seasonNum);

    }
    public void requestTvShowReviews(){
        tvShowRepository.requestTvShowReviews(tvId);

    }

    public void addTvShowToList(TVShow show){
        Log.d(TAG, "saveTvShowToList: Adding TV Show " + show.getTitle());
        savedItemRepository.insertSavedItem(new SavedItem(tvId,show.getTitle(),show.getPosterPath()));

    }
    public void removeTvShowFromList(TVShow show){
        Log.d(TAG, "removeTvShowFromList: Removing TV Show " + show.getTitle());
        savedItemRepository.deleteSavedItem(new SavedItem(tvId,show.getTitle(),show.getPosterPath()));
    }

    public LiveData<TVShow> getTvShowDetails() {
        return tvShowRepository.getTVShowDetails();
    }
    public LiveData<List<Season>> getSeasons() {
        LiveData<TVShow> tvShowDetails = tvShowRepository.getTVShowDetails();
        tvShowDetails.observeForever(tvShow -> {
            Collections.sort(tvShow.getSeasons(), new Comparator<Season>() {
                public int compare(Season obj1, Season obj2) {
                    // ## Ascending order
                    return Integer.valueOf(obj1.getSeasonNumber()).compareTo(Integer.valueOf(obj2.getSeasonNumber())); // To compare integer values

                }
            });

            getSeasonsLiveData.postValue(tvShow.getSeasons());
        });
        return getSeasonsLiveData;
    }

    public LiveData<List<Episode>> getEpisodes() {
        LiveData<Season> seasonLiveData = tvShowRepository.getTVShowSeasonLiveData();
        seasonLiveData.observeForever(season -> {

            episodesLiveData.postValue(season.getEpisodes());
        });
        return episodesLiveData;
    }

    public LiveData<Integer> getSelectedSeason() {
        return currentSelectedSeason;
    }

    public LiveData<List<Integer>> getEpisodeRunTime() {
        LiveData<TVShow> tvShowDetails = tvShowRepository.getTVShowDetails();
        tvShowDetails.observeForever(tvShow -> {
            episodeRunTimeLiveData.postValue(tvShow.getEpisodeRuntime());
        });

        return episodeRunTimeLiveData;
    }

    public LiveData<List<TVShow>> getSimilarTVShows() {
        return tvShowRepository.getRecommendationsLiveData();
    }
    public LiveData<List<Review>> getTvShowReviews(){
        return tvShowRepository.getTvShowReview();
    }
    public LiveData<Boolean> getTvShowExists(){
        return savedItemRepository.getSavedItemExists();
    }
}
