package com.example.pinayflix.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinayflix.R;
import com.example.pinayflix.adapter.recyclerview.tvshow.SeasonsTVShowAdapter;
import com.example.pinayflix.callback.OnSeasonSelect;
import com.example.pinayflix.databinding.LayoutSeasonListBinding;
import com.example.pinayflix.viewmodel.TVShowDetailsFragmentViewModel;


public class SeasonListDialog extends DialogFragment implements OnSeasonSelect {
    private LayoutSeasonListBinding binder;
    private ImageButton closeBtn;
    private RecyclerView seasonsRv;
    private SeasonsTVShowAdapter adapter;
    private TVShowDetailsFragmentViewModel viewModel;

    @Override
    public int getTheme() {
        return R.style.FullScreenDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            binder = LayoutSeasonListBinding.inflate(inflater,container,false);
            return binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(getParentFragment()).get(TVShowDetailsFragmentViewModel.class);

        super.onViewCreated(view, savedInstanceState);
        closeBtn = binder.closeBtn;
        seasonsRv = binder.seasonList;

        seasonsRv = binder.seasonList;
        LinearSnapHelper snapHelper  = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(seasonsRv);

        viewModel.getSeasons().observe(getViewLifecycleOwner(),seasons -> {
            adapter = new SeasonsTVShowAdapter(seasons,this);
            seasonsRv.setAdapter(adapter);
            viewModel.getSelectedSeason().observe(getViewLifecycleOwner(),selectedSeason ->{
                adapter.setSelected(selectedSeason);
                seasonsRv.scrollToPosition(selectedSeason);
            });

        });

        closeBtn.setOnClickListener(btn ->{
            dismiss();
        });
    }

    @Override
    public void onSeasonSelect(int season) {
        viewModel.requestSeason(season);
        dismiss();
    }
}
