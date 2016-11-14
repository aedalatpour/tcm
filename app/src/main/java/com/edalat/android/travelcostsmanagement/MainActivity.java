package com.edalat.android.travelcostsmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import Tools.DBHelper;
import Tools.Helper;
import controller.adapter.CostDetailsPagerAdapter;
import controller.adapter.MainPagerAdapter;

/**
 * Created by alireza on 03/10/2016.
 */

public class MainActivity extends AppCompatActivity {
    TextView txt_main_headertitle;
    TabLayout tabLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            new DBHelper(this).createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);

        findViewsById();

        setSupportActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.action_persons));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.action_trips));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MainPagerAdapter adapter = new MainPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    private void findViewsById() {
        txt_main_headertitle = (TextView) findViewById(R.id.txt_costs_headertitle);
        tabLayout  = (TabLayout) findViewById(R.id.tab_layout);
        toolbar =(Toolbar) findViewById(R.id.appbar);
        Helper.setTypeFace(this,txt_main_headertitle);

    }
}
