package com.example.pinayflix.adapter.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.pinayflix.ui.fragments.ReviewFragment;

public class ReviewVPAdapter extends FragmentStatePagerAdapter {
    private int count;
    public ReviewVPAdapter(@NonNull FragmentManager fm, int behavior,int count) {
        super(fm, behavior);
        this.count = count;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new ReviewFragment(position);
    }

    @Override
    public int getCount() {
        return count;
    }
}
