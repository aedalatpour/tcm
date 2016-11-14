package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.edalat.android.travelcostsmanagement.CostDefineActivity;
import com.edalat.android.travelcostsmanagement.R;

import Tools.DBHelper;
import controller.adapter.CostListAdapter;

/**
 * Created by alireza on 29/09/2016.
 */
public class Tab3CostsFragment extends Fragment {
    ListView lsv_costs_list;
    Button btn_costs_new;
    int mTripId;

    public Tab3CostsFragment(int tripId) {
        mTripId = tripId;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab3_costs, container, false);
        findViewsById(rootView);
        createCostList(rootView);

        btn_costs_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CostDefineActivity.class);
                intent.putExtra("Id", 0);
                intent.putExtra("tripId", mTripId);
                startActivity(intent);
            }
        });

        return  rootView;
    }
    private void createCostList(View view) {
        DBHelper dbHelper = new DBHelper(view.getContext());
        CostListAdapter adapter = new CostListAdapter(view.getContext(), dbHelper.getAllCosts(mTripId));
        final ListView listView = (ListView) view.findViewById(R.id.lsv_costs_list);
        listView.setAdapter(adapter);
    }

    private void findViewsById(View view) {
        lsv_costs_list = (ListView) view.findViewById(R.id.lsv_costs_list);
        btn_costs_new = (Button) view.findViewById(R.id.btn_costs_new);
    }
}
