package controller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fragments.MainTab1PersonsFragment;
import fragments.MainTab2TripsFragment;
import fragments.Tab1PersonsFragment;
import fragments.Tab2PaymentsFragment;
import fragments.Tab3CostsFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MainPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MainTab1PersonsFragment tab1 = new MainTab1PersonsFragment();
                return tab1;
            case 1:
                MainTab2TripsFragment tab2 = new MainTab2TripsFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}