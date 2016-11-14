package com.edalat.android.travelcostsmanagement;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Tools.DBHelper;
import Tools.Helper;
import Tools.JalaliCalendar;
import controller.adapter.PersonCalculationAdapter;
import controller.adapter.PersonListAdapter;
import fragments.DatePickerDialog;
import model.Person;
import model.Trip;

/**
 * Created by alireza on 12/09/2016.
 */
public class TripDefineActivity extends Activity {
    private static final int PERSONS_REQUEST = 1;
    private static final int PICTURE_REQUEST = 2;
    private String tripImagePath;
    Button btn_tripdefine_persons;
    Button btn_tripdefine_return;
    Button btn_tripdefine_save;
    Button btn_tripdefine_selectpicture;

    EditText txt_tripdefine_title;
    TextView txt_tripdefine_date;
    TextView txt_tripdefine_headertitle;
    TextView txt_tripdefine_titlelable;
    TextView txt_tripdefine_datelable;
    TextView txt_tripdefine_personslable;

    ImageView img_tripdefine_picture;
    Integer tripID;

    List<Integer> selectedPersonIDs = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripdefine);
        findViewById();

        Bundle bundle = getIntent().getExtras();
        tripID = Helper.GetInt(bundle.get("Id"));
        if (tripID != 0) {
            Trip trip = new DBHelper(this).getTrip(tripID);

            txt_tripdefine_title.setText(trip.getTitle());
            txt_tripdefine_date.setText(trip.getDate());
            selectedPersonIDs = trip.getPersons();
            createPersonList(this,selectedPersonIDs);
        }else {
            txt_tripdefine_date.setText(Helper.GetCurrentJalaiDate());
        }

        txt_tripdefine_date.setOnClickListener(new DatePickerDialog(this,txt_tripdefine_date));

        btn_tripdefine_persons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SelectPersonActivity.class);
                intent.putExtra("tripId",0);
                intent.putExtra("selectedIDs", new Gson().toJson(selectedPersonIDs));
                startActivityForResult(intent,PERSONS_REQUEST);
            }
        });
        btn_tripdefine_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_tripdefine_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Trip trip = new Trip();
                trip.setId(tripID);
                trip.setTitle(txt_tripdefine_title.getText().toString());
                trip.setDate(txt_tripdefine_date.getText().toString());
                trip.setImagePath(tripImagePath);
                trip.setPersons(selectedPersonIDs);
                new DBHelper(view.getContext()).setTripInfo(trip);
                finish();
            }
        });

        btn_tripdefine_selectpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String picPath = pictureDirectory.getPath();
                Uri data = Uri.parse(picPath);
                photoPicker.setDataAndType(data,"image/*");
                startActivityForResult(photoPicker,PICTURE_REQUEST);
            }
        });
    }

    private void findViewById() {
        btn_tripdefine_persons = (Button) findViewById(R.id.btn_tripdefine_persons);
        btn_tripdefine_return = (Button) findViewById(R.id.btn_tripdefine_return);
        btn_tripdefine_save = (Button) findViewById(R.id.btn_tripdefine_save);
        btn_tripdefine_selectpicture = (Button) findViewById(R.id.btn_tripdefine_selectpicture);
        txt_tripdefine_title = (EditText) findViewById(R.id.txt_tripdefine_title);
        txt_tripdefine_date = (TextView) findViewById(R.id.txt_tripdefine_date);
        img_tripdefine_picture = (ImageView) findViewById(R.id.img_tripdefine_picture);

        txt_tripdefine_headertitle = (TextView) findViewById(R.id.txt_tripdefine_headertitle);
        txt_tripdefine_titlelable = (TextView) findViewById(R.id.txt_tripdefine_titlelable);
        txt_tripdefine_datelable = (TextView) findViewById(R.id.txt_tripdefine_datelable);
        txt_tripdefine_personslable = (TextView) findViewById(R.id.txt_tripdefine_personslable);

        Helper.setTypeFace(this,txt_tripdefine_title);
        Helper.setTypeFace(this,txt_tripdefine_date);
        Helper.setTypeFace(this,txt_tripdefine_headertitle);
        Helper.setTypeFace(this,txt_tripdefine_titlelable);
        Helper.setTypeFace(this,txt_tripdefine_datelable);
        Helper.setTypeFace(this,txt_tripdefine_personslable);
        Helper.setTypeFace(this, btn_tripdefine_persons);
        Helper.setTypeFace(this, btn_tripdefine_return);
        Helper.setTypeFace(this, btn_tripdefine_save);
        Helper.setTypeFace(this, btn_tripdefine_selectpicture);

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if (resultCode == RESULT_OK && requestCode == PERSONS_REQUEST) {
            if (data.hasExtra("selectedIDs")){
                Type type = new TypeToken<List<Integer>>() {}.getType();
                selectedPersonIDs = new GsonBuilder().create().fromJson(data.getExtras().getString("selectedIDs"), type);
                createPersonList(this,selectedPersonIDs);
            }
        }else if (resultCode == RESULT_OK && requestCode == PICTURE_REQUEST){
            Uri imagePath = data.getData();
            InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(imagePath);
                tripImagePath = Helper.getRealPathFromURI(this, imagePath);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                img_tripdefine_picture.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unalble to open file", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void createPersonList(Context context,List<Integer> selectedIDs) {
        DBHelper dbHelper = new DBHelper(context);
        PersonCalculationAdapter adapter = new PersonCalculationAdapter(context, dbHelper.GetPersons(selectedIDs),tripID);
        final ListView listView = (ListView) findViewById(R.id.lsv_tripdefine_persons);
        listView.setAdapter(adapter);

    }

}
