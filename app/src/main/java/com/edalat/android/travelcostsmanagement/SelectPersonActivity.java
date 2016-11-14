package com.edalat.android.travelcostsmanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import Tools.DBHelper;
import Tools.Helper;
import controller.adapter.PersonListAdapter;
import model.Person;

/**
 * Created by alireza on 13/09/2016.
 */
public class SelectPersonActivity extends Activity {
    Button btn_tripdefine_return;
    Button btn_selectperson_select;
    ListView listView;
    PersonListAdapter adapter;
    Integer tripId;
    List<Integer> selectedIds;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectperson);
        Bundle bundle = getIntent().getExtras();
        tripId = Helper.GetInt(bundle.get("tripId"));
        Type type = new TypeToken<List<Integer>>() {}.getType();
        selectedIds = new GsonBuilder().create().fromJson(bundle.getString("selectedIDs"), type);
        if (selectedIds == null)
            selectedIds = new ArrayList<Integer>();
        findViewsById();

        createPersonList(this);

        btn_tripdefine_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_selectperson_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> selectedIDs = adapter.getSelectedItems();
                //String json = new Gson
                String gson = new Gson().toJson(selectedIDs);

                Intent intent = new Intent();
                intent.putExtra("selectedIDs",gson);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void findViewsById() {
        listView = (ListView) findViewById(R.id.lsv_selectperson_list);
        btn_selectperson_select = (Button) findViewById(R.id.btn_selectperson_select);
        btn_tripdefine_return = (Button) findViewById(R.id.btn_tripdefine_return);
    }
    private void createPersonList(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        List<Person> persons = null;
        if (tripId == 0)
            persons = dbHelper.GetAllPersons();
        else
            persons = dbHelper.GetPersons(tripId);

        adapter = new PersonListAdapter(context, persons ,true,selectedIds);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
    }

}
