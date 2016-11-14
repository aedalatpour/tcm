package com.edalat.android.travelcostsmanagement;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import Tools.DBHelper;
import Tools.Helper;
import controller.adapter.BillAdapter;
import model.BillDetails;

import static Tools.Helper.GetFormatedNumber;

/**
 * Created by alireza on 02/10/2016.
 */

public class BillActivity extends Activity{
    Button btn_bill_return;
    ListView lsv_bill_details;
    TextView txt_bill_headertext;
    TextView txt_bill_paymentlable;
    TextView txt_bill_costlable;
    TextView txt_bill_title;
    TextView txt_bill_sumpayment;
    TextView txt_bill_sumcosts;
    TextView txt_bill_sumlable;
    TextView txt_bill_amount;
    TextView txt_bill_amounttitle;

    Integer mTripId;
    Integer mPersonId;

    Integer totalPayment;
    Integer totalCost;
    Integer totalAmount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        findViewsById();

        Bundle bundle = getIntent().getExtras();
        mPersonId = Helper.GetInt(bundle.get("personId"));
        mTripId = Helper.GetInt(bundle.get("tripId"));
        DBHelper db = new DBHelper(this);
        txt_bill_headertext.setText("صورتحساب" + " : " + db.getTrip(mTripId).getTitle() + " > " + db.GetPerson(mPersonId).getName());
        createList(this);
        btn_bill_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void findViewsById() {
        btn_bill_return = (Button) findViewById(R.id.btn_bill_return);
        lsv_bill_details = (ListView) findViewById(R.id.lsv_bill_details);
        txt_bill_headertext = (TextView) findViewById(R.id.txt_bill_headertext);
        txt_bill_paymentlable = (TextView) findViewById(R.id.txt_bill_paymentlable);
        txt_bill_costlable= (TextView) findViewById(R.id.txt_bill_costlable);
        txt_bill_title= (TextView) findViewById(R.id.txt_bill_title);
        txt_bill_sumpayment= (TextView) findViewById(R.id.txt_bill_sumpayment);
        txt_bill_sumcosts= (TextView) findViewById(R.id.txt_bill_sumcosts);
        txt_bill_sumlable= (TextView) findViewById(R.id.txt_bill_sumlable);
        txt_bill_amount= (TextView) findViewById(R.id.txt_bill_amount);
        txt_bill_amounttitle= (TextView) findViewById(R.id.txt_bill_amounttitle);

        Helper.setTypeFace(this,btn_bill_return);
        Helper.setTypeFace(this,txt_bill_headertext);
        Helper.setTypeFace(this,txt_bill_paymentlable);
        Helper.setTypeFace(this,txt_bill_costlable);
        Helper.setTypeFace(this,txt_bill_title);
        Helper.setTypeFace(this,txt_bill_sumpayment);
        Helper.setTypeFace(this,txt_bill_sumcosts);
        Helper.setTypeFace(this,txt_bill_sumlable);
        Helper.setTypeFace(this,txt_bill_amount);
        Helper.setTypeFace(this,txt_bill_amounttitle);
    }
    public void createList(Context context){
        DBHelper dbHelper = new DBHelper(context);
        List<BillDetails> billDetailsList = dbHelper.getBillDetails(mTripId,mPersonId);
        totalPayment = 0;
        totalCost = 0;
        totalAmount = 0;

        for (int i = 0;i< billDetailsList.size();i++){
            totalPayment += billDetailsList.get(i).getPaymentAmount();
            totalCost += billDetailsList.get(i).getCostAmount();
        }
        totalAmount = totalCost - totalPayment;

        int textcolor = totalAmount > 0 ? Color.BLUE : (totalAmount == 0 ? Color.GREEN : Color.RED);
        String texttitle = totalAmount > 0 ? "بدهکار" : (totalAmount == 0 ? "تسویه" : "طلبکار");
        txt_bill_sumcosts.setText(GetFormatedNumber(totalCost));
        txt_bill_sumpayment.setText(GetFormatedNumber(totalPayment));
        txt_bill_amount.setText(GetFormatedNumber(totalAmount));
        txt_bill_amounttitle.setText(texttitle);
        txt_bill_amounttitle.setTextColor(textcolor);
        txt_bill_amount.setTextColor(textcolor);
        BillAdapter adapter = new BillAdapter(context,billDetailsList);
        final ListView listView = (ListView) findViewById(R.id.lsv_bill_details);
        listView.setAdapter(adapter);
    }


}
