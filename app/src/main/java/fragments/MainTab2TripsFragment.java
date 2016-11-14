package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.edalat.android.travelcostsmanagement.PersonDefineActivity;
import com.edalat.android.travelcostsmanagement.R;
import com.edalat.android.travelcostsmanagement.TripDefineActivity;

import java.util.ArrayList;

import Tools.DBHelper;
import controller.adapter.PersonListAdapter;
import controller.adapter.TripListAdapter;
import model.Person;
import model.Trip;

/**
 * Created by alireza on 29/09/2016.
 */
public class MainTab2TripsFragment extends Fragment {
    ListView lsv_main_list;
    Button btn_main_new;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_maintab1_persons, container, false);
        lsv_main_list = (ListView) rootView.findViewById(R.id.lsv_main_list);
        btn_main_new = (Button) rootView.findViewById(R.id.btn_main_new);
        createPersonList(rootView);
        btn_main_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(rootView.getContext(),TripDefineActivity.class);
                intent.putExtra("Id",0);
                startActivity(intent);
            }
        });

        return  rootView;
    }

    private void createPersonList(View view) {
        DBHelper dbHelper = new DBHelper(view.getContext());
        TripListAdapter adapter = new TripListAdapter(view.getContext(),dbHelper.GetAllTrips());
        lsv_main_list.setAdapter(adapter);
    }
}
