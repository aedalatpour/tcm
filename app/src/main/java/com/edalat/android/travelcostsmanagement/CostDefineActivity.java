package com.edalat.android.travelcostsmanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Common.DistributionType;
import Tools.DBHelper;
import Tools.Helper;
import controller.adapter.PersonCalculationAdapter;
import controller.adapter.PersonCostCalculationAdapter;
import model.Cost;
import model.Person;

/**
 * Created by alireza on 16/09/2016.
 */
public class CostDefineActivity extends Activity {
    static final Integer PERSON_REQUEST = 1;

    Integer costId;
    Integer tripId;

    //region Controls
    Spinner spn_costdefine_paidby;
    Spinner spn_costdefine_distributiontype;
    Spinner spn_costdefine_coveredpersons;
    Button btn_costdefine_return;
    Button btn_costdefine_save;
    Switch swh_costdefine_custommode;
    EditText txt_costdefine_title;
    EditText txt_costdefine_amount;
    ListView lsv_costdefine_preview;
    TextView txt_costdefine_titlelable;
    TextView txt_costdefine_amountlable;
    TextView txt_costdefine_paidbylable;
    TextView txt_costdefine_distributiontypelable;
    TextView txt_costdefine_coveredpersonslable;
    TextView txt_costdefine_headertext;
    TextView txt_costdefine_sum;
    TextView txt_costdefine_sumamount;
    //endregion

    List<Person> persons;
    List<Integer> currentCostPersonIds = new ArrayList<Integer>();
    boolean spn_distributiontype_init;
    boolean spn_coveredpersons_init;
    boolean isCustomMode = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costdefine);
        findViewsById();

        Bundle bundle = getIntent().getExtras();
        DBHelper dbHelper = new DBHelper(this);
        costId = Helper.GetInt(bundle.get("Id"));
        tripId = Helper.GetInt(bundle.get("tripId"));
        loadPersons(dbHelper);
        spn_distributiontype_init = false;
        spn_coveredpersons_init = false;

        loadSpinnerData();
        if (costId != 0){
            Cost cost = new DBHelper(this).GetCost(costId);
            txt_costdefine_title.setText(cost.getTitle());
            txt_costdefine_amount.setText(cost.getTotalAmount().toString());
            if (cost.getPayerPersonId() > 0)
                spn_costdefine_paidby.setSelection(getPosition(persons,cost.getPayerPersonId()));
            createPreviewList(this, getToalAmount(), getDistributionType(),dbHelper.getCostDetails(costId) );
        }
        swh_costdefine_custommode.setChecked(isCustomMode);

        swh_costdefine_custommode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCustomMode = b;
            }
        });


        btn_costdefine_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        spn_costdefine_coveredpersons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 1) /*SelectPersons*/ {
                    Intent personselectIntent = new Intent(view.getContext(), SelectPersonActivity.class);
                    personselectIntent.putExtra("tripId", tripId);
                    personselectIntent.putExtra("selectedIDs", new Gson().toJson(currentCostPersonIds));
                    startActivityForResult(personselectIntent, PERSON_REQUEST);

                } else if (i == 0 && !spn_coveredpersons_init) {
                    loadPersons(new DBHelper(view.getContext()));
                    createPreviewList(view.getContext(),getToalAmount(),getDistributionType(),new HashMap<Integer, Integer>());
                }
                spn_coveredpersons_init = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        spn_costdefine_distributiontype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_distributiontype_init) {

                    if (costId == 0 || !isCustomMode)
                        createPreviewList(view.getContext(), getToalAmount(), getDistributionType(), new HashMap<Integer, Integer>());
                    else if (isCustomMode)
                        createPreviewList(view.getContext(), getToalAmount(), getDistributionType(), getCurrentData());
//                    else
//                        createPreviewList(view.getContext(), getToalAmount(), getDistributionType(), new HashMap<Integer, Integer>());
                }
                spn_distributiontype_init = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        btn_costdefine_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonCostCalculationAdapter currentAdapter = (PersonCostCalculationAdapter) lsv_costdefine_preview.getAdapter();
                if (currentAdapter.getSum() - Helper.GetInt(txt_costdefine_amount.getText()) > 10 ||
                        currentAdapter.getSum() - Helper.GetInt(txt_costdefine_amount.getText()) < -10){
                    Toast.makeText(getApplicationContext(),R.string.message_amountconfilict,Toast.LENGTH_SHORT);
                }else {
                    DBHelper db = new DBHelper(view.getContext());
                    Cost cost = new Cost();
                    cost.setTripId(tripId);
                    cost.setTitle(txt_costdefine_title.getText().toString());
                    cost.setTotalAmount(Helper.GetInt(txt_costdefine_amount.getText()));
                    cost.setId(costId);
                    cost.setPayerPersonId(persons.get(spn_costdefine_paidby.getSelectedItemPosition()).getId());
                    cost.setCostDetails(currentAdapter.getValues());
                    db.setCostInfo(cost);
                    finish();
                }
            }
        });
    }
    private void loadPersons(DBHelper dbHelper){
    //    DBHelper dbHelper = new DBHelper(ctx);
        persons = dbHelper.GetPersons(tripId);
        currentCostPersonIds.clear();
        for (int i = 0;i < persons.size();i++)
            currentCostPersonIds.add(persons.get(i).getId());
    }

    public void onDialogRespond(){
        swh_costdefine_custommode.setChecked(true);
        spn_distributiontype_init = false;
        spn_coveredpersons_init = false;
//        createPreviewList(this, getToalAmount(), getDistributionType(), getCurrentData());
        spn_costdefine_distributiontype.setSelection(2);
        lsv_costdefine_preview.requestFocus();
        txt_costdefine_sumamount.setText(getListAdapter().getSum().toString());
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if (resultCode == RESULT_OK && requestCode == PERSON_REQUEST) {
            if (data.hasExtra("selectedIDs")){
                Type type = new TypeToken<List<Integer>>() {}.getType();
                currentCostPersonIds = new GsonBuilder().create().fromJson(data.getExtras().getString("selectedIDs"), type);
                if (isCustomMode)
                    createPreviewList(this,getToalAmount(),getDistributionType(),getListAdapter().getValues());
                else
                    createPreviewList(this,getToalAmount(),getDistributionType(),new HashMap<Integer, Integer>());
            }
        }
    }
    private void createPreviewList(Context context,Integer totalAmount, DistributionType type,HashMap<Integer, Integer> data) {
        DBHelper dbHelper = new DBHelper(context);
        PersonCostCalculationAdapter adapter = new PersonCostCalculationAdapter(context, dbHelper.GetPersons(currentCostPersonIds),type,totalAmount,data);
        final ListView listView = (ListView) findViewById(R.id.lsv_costdefine_preview);
        listView.setAdapter(adapter);
        txt_costdefine_sumamount.setText(adapter.getSum().toString());
    }

    private void findViewsById() {
        spn_costdefine_paidby = (Spinner) findViewById(R.id.spn_costdefine_paidby);
        spn_costdefine_distributiontype = (Spinner) findViewById(R.id.spn_costdefine_distributiontype);
        spn_costdefine_coveredpersons = (Spinner) findViewById(R.id.spn_costdefine_coveredpersons);
        btn_costdefine_return = (Button) findViewById(R.id.btn_costdefine_return);
        btn_costdefine_save = (Button) findViewById(R.id.btn_costdefine_save);
        swh_costdefine_custommode = (Switch) findViewById(R.id.swh_costdefine_custommode );
        txt_costdefine_title = (EditText) findViewById(R.id.txt_costdefine_title);
        txt_costdefine_amount = (EditText) findViewById(R.id.txt_costdefine_amount);
        lsv_costdefine_preview = (ListView) findViewById(R.id.lsv_costdefine_preview);

        txt_costdefine_titlelable = (TextView) findViewById(R.id.txt_costdefine_titlelable);
        txt_costdefine_amountlable = (TextView) findViewById(R.id.txt_costdefine_amountlable);
        txt_costdefine_paidbylable = (TextView) findViewById(R.id.txt_costdefine_paidbylable);
        txt_costdefine_distributiontypelable = (TextView) findViewById(R.id.txt_costdefine_distributiontypelable);
        txt_costdefine_coveredpersonslable = (TextView) findViewById(R.id.txt_costdefine_coveredpersonslable);
        txt_costdefine_headertext = (TextView) findViewById(R.id.txt_costdefine_headertext);
        txt_costdefine_sum = (TextView) findViewById(R.id.txt_costdefine_sum);
        txt_costdefine_sumamount = (TextView) findViewById(R.id.txt_costdefine_sumamount);

        Helper.setTypeFace(this,txt_costdefine_amount);
        Helper.setTypeFace(this,txt_costdefine_title);
        Helper.setTypeFace(this,txt_costdefine_titlelable);
        Helper.setTypeFace(this,txt_costdefine_amountlable);
        Helper.setTypeFace(this,txt_costdefine_paidbylable);
        Helper.setTypeFace(this,txt_costdefine_distributiontypelable);
        Helper.setTypeFace(this,txt_costdefine_coveredpersonslable);
        Helper.setTypeFace(this,txt_costdefine_headertext);
        Helper.setTypeFace(this,txt_costdefine_sum);
        Helper.setTypeFace(this,txt_costdefine_sumamount);
        Helper.setTypeFace(this,swh_costdefine_custommode);
        Helper.setTypeFace(this,btn_costdefine_return);
        Helper.setTypeFace(this,btn_costdefine_save);



    }
    private void loadSpinnerData() {
        // Spinner Drop down elements
        List<String> lables = new ArrayList<String>();
        for (int i = 0;i < persons.size();i++)
            lables.add(persons.get(i).getName());

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spn_costdefine_paidby.setAdapter(dataAdapter);
    }
    private Integer getPosition(List<Person> persons,Integer personId){
        Integer pos = null;
        for (int i = 0;i < persons.size();i++){
            if (persons.get(i).getId() == personId)
                pos = i;
        }
        return pos;
    }
    private Integer getToalAmount(){

        return Helper.GetInt(txt_costdefine_amount.getText());

    }
    private DistributionType getDistributionType(){
        DistributionType t =null;
        if (spn_costdefine_distributiontype.getSelectedItemPosition() == 0)
            t = DistributionType.Proportional;
        else if (spn_costdefine_distributiontype.getSelectedItemPosition() == 1)
            t = DistributionType.Equal;
        else if (spn_costdefine_distributiontype.getSelectedItemPosition() == 2)
            t = DistributionType.Different;
        return  t;
    }
    private HashMap<Integer,Integer> getCurrentData(){
        return ((PersonCostCalculationAdapter) lsv_costdefine_preview.getAdapter()).getValues();
    }
    private PersonCostCalculationAdapter getListAdapter(){
        return (PersonCostCalculationAdapter) lsv_costdefine_preview.getAdapter();
    }
    private Integer getAdapterSum(HashMap<Integer, Integer> data){
        Integer ret =0;
        for(int i = 0;i < data.size();i++)
            ret += data.get(i);
        return ret;
    }
}
