package com.example.pinayflix.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pinayflix.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = FragmentSearchBinding.inflate(inflater,container,false);
        return binder.getRoot();

    }
}
