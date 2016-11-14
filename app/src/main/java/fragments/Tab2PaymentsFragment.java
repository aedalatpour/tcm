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
import com.edalat.android.travelcostsmanagement.PaymentDefineActivity;
import com.edalat.android.travelcostsmanagement.R;

import Tools.DBHelper;
import controller.adapter.CostListAdapter;
import controller.adapter.PaymentListAdapter;

/**
 * Created by alireza on 29/09/2016.
 */
public class Tab2PaymentsFragment extends Fragment {
    ListView lsv_payments_list;
    Button btn_payments_new;
    int mTripId;

    public Tab2PaymentsFragment(int tripId) {
        mTripId = tripId;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab2_payments, container, false);
        findViewsById(rootView);
        createPaymentList(rootView);

        btn_payments_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PaymentDefineActivity.class);
                intent.putExtra("Id", 0);
                intent.putExtra("tripId", mTripId);
                startActivity(intent);
            }
        });

        return  rootView;
    }
    private void createPaymentList(View view) {
        DBHelper dbHelper = new DBHelper(view.getContext());
        PaymentListAdapter adapter = new PaymentListAdapter(view.getContext(), dbHelper.getAllPayments(mTripId));
        final ListView listView = (ListView) view.findViewById(R.id.lsv_payments_list);
        listView.setAdapter(adapter);
    }

    private void findViewsById(View view) {
        lsv_payments_list = (ListView) view.findViewById(R.id.lsv_payments_list);
        btn_payments_new = (Button) view.findViewById(R.id.btn_payments_new);
    }
}
