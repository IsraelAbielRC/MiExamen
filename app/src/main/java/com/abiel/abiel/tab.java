package com.abiel.abiel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.abiel.abiel.Controller.GridViewAdapter;
import com.abiel.abiel.Controller.PagerController;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

public class tab extends AppCompatActivity {
    TabLayout _tabLayout;
    TabItem _tabApi, _tabUbicacion, _tabImaganes;
    PagerController _pageController;
    ViewPager _pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tab);
        _tabLayout = findViewById(R.id.tablayout);
        _tabApi = findViewById(R.id.tabapi);
        _tabUbicacion = findViewById(R.id.tabubicacion);
        _tabImaganes = findViewById(R.id.tabimagenes);
        _pager = findViewById(R.id.viewpager);
        _pageController = new PagerController(getSupportFragmentManager(),_tabLayout.getTabCount());
        _pager.setAdapter(_pageController);
        _tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                _pager.setCurrentItem(tab.getPosition());
                _pageController.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        _pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_tabLayout));

    }


}