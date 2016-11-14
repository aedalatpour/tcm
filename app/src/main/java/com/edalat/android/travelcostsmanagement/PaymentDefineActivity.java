package com.edalat.android.travelcostsmanagement;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Tools.DBHelper;
import Tools.Helper;
import fragments.DatePickerDialog;
import model.Cost;
import model.Payment;
import model.Person;

/**
 * Created by alireza on 02/10/2016.
 */

public class PaymentDefineActivity extends Activity {
    Integer paymentId;
    Integer tripId;
    List<Person> persons;

    Button btn_paymentdefine_return;
    Button btn_paymentdefine_save;
    TextView txt_paymentdefine_headertext;
    TextView txt_paymentdefine_titlelable;
    TextView txt_paymentdefine_datelable;
    TextView txt_paymentdefine_date;
    TextView txt_paymentdefine_amountlable;
    TextView txt_paymentdefine_paidbylable;
    TextView txt_paymentdefine_receiverlable;
    EditText txt_paymentdefine_title;
    EditText txt_paymentdefine_amount;
    Spinner spn_paymentdefine_paidby;
    Spinner spn_paymentdefine_receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentdefine);
        findViewsById();

        Bundle bundle = getIntent().getExtras();
        DBHelper dbHelper = new DBHelper(this);
        paymentId = Helper.GetInt(bundle.get("Id"));
        tripId = Helper.GetInt(bundle.get("tripId"));
        persons = dbHelper.GetPersons(tripId);
        loadSpinnerData();
        if (paymentId != 0){
            Payment payment = new DBHelper(this).GetPayment(paymentId);
            txt_paymentdefine_title.setText(payment.getTitle());
            txt_paymentdefine_date.setText(payment.getDate());
            txt_paymentdefine_amount.setText(payment.getAmount().toString());
            if (payment.getPayerPersonId() > 0)
                spn_paymentdefine_paidby.setSelection(getPosition(persons,payment.getPayerPersonId()));
            if (payment.getReceiverPersonId() > 0)
                spn_paymentdefine_receiver.setSelection(getPosition(persons,payment.getReceiverPersonId()));
        }
        txt_paymentdefine_date.setOnClickListener(new DatePickerDialog(this,txt_paymentdefine_date));
        btn_paymentdefine_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_paymentdefine_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHelper db = new DBHelper(view.getContext());
                Payment payment = new Payment();
                payment.setTripId(tripId);
                payment.setTitle(txt_paymentdefine_title.getText().toString());
                payment.setDate(txt_paymentdefine_date.getText().toString());
                payment.setAmount(Helper.GetInt(txt_paymentdefine_amount.getText()));
                payment.setId(paymentId);
                payment.setPayerPersonId(persons.get(spn_paymentdefine_paidby.getSelectedItemPosition()).getId());
                payment.setReceiverPersonId(persons.get(spn_paymentdefine_receiver.getSelectedItemPosition()).getId());
                db.setPaymentInfo(payment);
                finish();
            }
        });
    }
    private void findViewsById() {
        btn_paymentdefine_return = (Button) findViewById(R.id.btn_paymentdefine_return);
        btn_paymentdefine_save = (Button) findViewById(R.id.btn_paymentdefine_save);
        txt_paymentdefine_headertext = (TextView) findViewById(R.id.txt_paymentdefine_headertext);
        txt_paymentdefine_titlelable = (TextView) findViewById(R.id.txt_paymentdefine_titlelable);
        txt_paymentdefine_datelable = (TextView) findViewById(R.id.txt_paymentdefine_datelable);
        txt_paymentdefine_date = (TextView) findViewById(R.id.txt_paymentdefine_date);
        txt_paymentdefine_amountlable = (TextView) findViewById(R.id.txt_paymentdefine_amountlable);
        txt_paymentdefine_paidbylable = (TextView) findViewById(R.id.txt_paymentdefine_paidbylable);
        txt_paymentdefine_receiverlable = (TextView) findViewById(R.id.txt_paymentdefine_receiverlable);
        txt_paymentdefine_title = (EditText) findViewById(R.id.txt_paymentdefine_title);
        txt_paymentdefine_amount = (EditText) findViewById(R.id.txt_paymentdefine_amount);
        spn_paymentdefine_paidby = (Spinner) findViewById(R.id.spn_paymentdefine_paidby);
        spn_paymentdefine_receiver = (Spinner) findViewById(R.id.spn_paymentdefine_receiver);


        Helper.setTypeFace(this,btn_paymentdefine_return);
        Helper.setTypeFace(this,btn_paymentdefine_save);
        Helper.setTypeFace(this,txt_paymentdefine_headertext);
        Helper.setTypeFace(this,txt_paymentdefine_titlelable);
        Helper.setTypeFace(this,txt_paymentdefine_datelable);
        Helper.setTypeFace(this,txt_paymentdefine_date);
        Helper.setTypeFace(this,txt_paymentdefine_amountlable);
        Helper.setTypeFace(this,txt_paymentdefine_paidbylable);
        Helper.setTypeFace(this,txt_paymentdefine_receiverlable);
        Helper.setTypeFace(this,txt_paymentdefine_title);
        Helper.setTypeFace(this,txt_paymentdefine_amount);
    }
    private void loadSpinnerData() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0;i < persons.size();i++)
            lables.add(persons.get(i).getName());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_paymentdefine_paidby.setAdapter(dataAdapter);
        spn_paymentdefine_receiver.setAdapter(dataAdapter);
    }
    private Integer getPosition(List<Person> persons,Integer personId){
        Integer pos = null;
        for (int i = 0;i < persons.size();i++){
            if (persons.get(i).getId() == personId)
                pos = i;
        }
        return pos;
    }
}
