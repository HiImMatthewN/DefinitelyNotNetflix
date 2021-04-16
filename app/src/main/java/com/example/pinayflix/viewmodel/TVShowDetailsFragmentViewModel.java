package com.example.pinayflix.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.pinayflix.model.datamodel.tvshow.TVShowDetails;
import com.example.pinayflix.repository.TVShowRepository;
import com.example.pinayflix.ui.fragments.TVShowDetailsFragment;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class TVShowDetailsFragmentViewModel extends ViewModel {
    private TVShowRepository tvShowRepository;
    private int tvId;

    @Inject
    public TVShowDetailsFragmentViewModel(SavedStateHandle savedStateHandle,TVShowRepository tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
        tvId =  savedStateHandle.get(TVShowDetailsFragment.DETAILS_KEY);
        requestTvShowDetails(tvId);
    }

    private void requestTvShowDetails(int tvId){
        tvShowRepository.requestTvShowDetails(tvId);

    }
    public LiveData<TVShowDetails> getTvShowDetails(){
        return tvShowRepository.getTVShowDetails();
    }
}
