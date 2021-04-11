package com.example.pinayflix.viewmodel.custom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.pinayflix.viewmodel.MovieDetailsFragmentViewModel;

public class ViewModelFactoryWithParams implements ViewModelProvider.Factory {
    private String stringParam;
    private Integer intParam;
    private Double doubleParam;
    private  Boolean booleanParam;
    public ViewModelFactoryWithParams( String mParam) {
        this.stringParam = mParam;
    }

    public ViewModelFactoryWithParams( Integer intParam) {
        this.intParam = intParam;
    }

    public ViewModelFactoryWithParams( Double doubleParam) {
        this.doubleParam = doubleParam;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetailsFragmentViewModel( intParam);
    }
}
