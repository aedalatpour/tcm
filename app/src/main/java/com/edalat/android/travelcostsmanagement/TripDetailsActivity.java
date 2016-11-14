package com.edalat.android.travelcostsmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Tools.DBHelper;
import Tools.Helper;
import controller.adapter.CostDetailsPagerAdapter;

/**
 * Created by alireza on 29/09/2016.
 */
public class TripDetailsActivity extends AppCompatActivity {
    Button btn_costs_return;
    Button btn_costs_edittrip;
    TextView txt_costs_headertitle;
    TabLayout tabLayout;
    int mTripId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripdetails);
        findViewsById();

        Bundle bundle = getIntent().getExtras();
        mTripId = Helper.GetInt(bundle.get("Id"));
        txt_costs_headertitle.setText(new DBHelper(this).getTrip(mTripId).getTitle());


        tabLayout.addTab(tabLayout.newTab().setText(R.string.action_persons));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.action_payments));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.action_costs));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final CostDetailsPagerAdapter adapter = new CostDetailsPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(),mTripId);
        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(2);
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

        btn_costs_edittrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TripDefineActivity.class);
                intent.putExtra("Id", mTripId);
                startActivity(intent);
            }
        });
        btn_costs_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void findViewsById() {
        btn_costs_return = (Button) findViewById(R.id.btn_costs_return);
        btn_costs_edittrip = (Button) findViewById(R.id.btn_costs_edittrip);
        txt_costs_headertitle = (TextView) findViewById(R.id.txt_costs_headertitle);
        tabLayout  = (TabLayout) findViewById(R.id.tab_layout);
        Helper.setTypeFace(this,txt_costs_headertitle);
        Helper.setTypeFace(this,btn_costs_return);
        Helper.setTypeFace(this,btn_costs_edittrip);

    }
}
