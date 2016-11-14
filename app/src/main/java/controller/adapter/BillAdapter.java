package controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edalat.android.travelcostsmanagement.R;

import java.util.List;

import Tools.Helper;
import model.BillDetails;

import static Tools.Helper.GetFormatedNumber;

/**
 * Created by alireza on 02/10/2016.
 */

public class BillAdapter extends BaseAdapter {
    Context mContext;
    List<BillDetails> mBillDetails;

    TextView txt_itembill_payments ;
    TextView txt_itembill_costs ;
    TextView txt_itembill_title ;

    public BillAdapter(Context ctx,List<BillDetails> billDetails){
        this.mContext = ctx;
        this.mBillDetails = billDetails;

    }
    @Override
    public int getCount() {
        return mBillDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_billdetails, null);
        findViewById(rootView);

        BillDetails billDetails = mBillDetails.get(position);

        txt_itembill_title.setText(billDetails.getTitle());
        txt_itembill_payments.setText(billDetails.getPaymentAmount() > 0 ? GetFormatedNumber(billDetails.getPaymentAmount()) : "");
        txt_itembill_costs.setText(billDetails.getCostAmount() > 0 ? GetFormatedNumber(billDetails.getCostAmount()) : "");

        return rootView;
    }
    private void findViewById(View rootView){
        txt_itembill_payments = (TextView) rootView.findViewById(R.id.txt_itembill_payments);
        txt_itembill_costs = (TextView) rootView.findViewById(R.id.txt_itembill_costs);
        txt_itembill_title = (TextView) rootView.findViewById(R.id.txt_itembill_title);

        Helper.setTypeFace(rootView.getContext(),txt_itembill_payments);
        Helper.setTypeFace(rootView.getContext(),txt_itembill_costs);
        Helper.setTypeFace(rootView.getContext(),txt_itembill_title);

    }
}
