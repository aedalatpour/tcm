package controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edalat.android.travelcostsmanagement.CostDefineActivity;
import com.edalat.android.travelcostsmanagement.PaymentDefineActivity;
import com.edalat.android.travelcostsmanagement.R;

import java.util.List;

import Tools.DBHelper;
import Tools.Helper;
import model.Cost;
import model.Payment;

import static Tools.Helper.GetFormatedNumber;

/**
 * Created by alireza on 15/09/2016.
 */
public class PaymentListAdapter extends BaseAdapter {
    Context mContext;
    List<Payment> mPaymentList;

    TextView txt_itempayment_title ;
    TextView txt_itempayment_smalltitle ;
    TextView txt_itempayment_amount ;

    public PaymentListAdapter(Context ctx, List<Payment> payment){
        this.mContext = ctx;
        this.mPaymentList = payment;

    }
    @Override
    public int getCount() {
        return mPaymentList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_costs, null);
        findViewById(rootView);

        Payment payment = mPaymentList.get(i);
        DBHelper dbHelper = new DBHelper(mContext);
        txt_itempayment_title.setText(payment.getTitle());
        txt_itempayment_smalltitle.setText(dbHelper.GetPerson(payment.getPayerPersonId()).getName() + " > " + dbHelper.GetPerson(payment.getReceiverPersonId()).getName());
        txt_itempayment_amount.setText(GetFormatedNumber(payment.getAmount()));

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PaymentDefineActivity.class);
                intent.putExtra("Id", mPaymentList.get(i).getId().toString());
                intent.putExtra("tripId", mPaymentList.get(i).getTripId().toString());
                mContext.startActivity(intent);
            }
        });


        return rootView;
    }
    private void findViewById(View rootView){
        txt_itempayment_title = (TextView) rootView.findViewById(R.id.txt_itemcost_title);
        txt_itempayment_smalltitle = (TextView) rootView.findViewById(R.id.txt_itemcost_smalltitle);
        txt_itempayment_amount = (TextView) rootView.findViewById(R.id.txt_itemcost_amount);

        Helper.setTypeFace(rootView.getContext(),txt_itempayment_title);
        Helper.setTypeFace(rootView.getContext(),txt_itempayment_smalltitle);
        Helper.setTypeFace(rootView.getContext(),txt_itempayment_amount);

    }
}
