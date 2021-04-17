package com.example.pinayflix.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.pinayflix.R;
import com.example.pinayflix.databinding.LayoutFullscreenProgressbarBinding;


public class ProgressBarDialog extends DialogFragment {
    private LayoutFullscreenProgressbarBinding binder;

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            binder = LayoutFullscreenProgressbarBinding.inflate(inflater,container,false);
            return binder.getRoot();
    }


}
