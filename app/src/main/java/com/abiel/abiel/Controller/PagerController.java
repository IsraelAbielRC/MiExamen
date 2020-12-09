package com.abiel.abiel.Controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerController extends FragmentPagerAdapter {
    int numTabs;

    public PagerController(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        numTabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case  0:
                return  new Movie();
            case  1:
                return  new ubicacion();
            case 2:
                return  new imagenes();
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
