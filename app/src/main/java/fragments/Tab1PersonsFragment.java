package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.edalat.android.travelcostsmanagement.R;

import Tools.DBHelper;
import controller.adapter.PaymentListAdapter;
import controller.adapter.PersonCalculationAdapter;

/**
 * Created by alireza on 29/09/2016.
 */
public class Tab1PersonsFragment extends Fragment {
    ListView lsv_trippersons_list;

    int mTripId;

    public Tab1PersonsFragment(int tripId) {
        mTripId = tripId;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab1_persons, container, false);
        lsv_trippersons_list = (ListView) rootView.findViewById(R.id.lsv_trippersons_list);
        createPersonList(rootView);

        return  rootView;
    }

    private void createPersonList(View view) {
        DBHelper dbHelper = new DBHelper(view.getContext());
        PersonCalculationAdapter adapter = new PersonCalculationAdapter(view.getContext(), dbHelper.GetPersons(mTripId),mTripId);
        lsv_trippersons_list.setAdapter(adapter);
    }
}
