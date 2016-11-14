package controller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fragments.Tab1PersonsFragment;
import fragments.Tab2PaymentsFragment;
import fragments.Tab3CostsFragment;

public class CostDetailsPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int mTripId;
    public CostDetailsPagerAdapter(FragmentManager fm, int NumOfTabs, int tripId) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.mTripId = tripId;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Tab1PersonsFragment tab1 = new Tab1PersonsFragment(mTripId);
                return tab1;
            case 1:
                Tab2PaymentsFragment tab2 = new Tab2PaymentsFragment(mTripId);
                return tab2;
            case 2:
                Tab3CostsFragment tab3 = new Tab3CostsFragment(mTripId);
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}