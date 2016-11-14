package com.edalat.android.travelcostsmanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import Tools.DBHelper;
import Tools.Helper;
import model.Person;

/**
 * Created by alireza on 01/09/2016.
 */
public class PersonDefineActivity extends Activity {
    private static final int PICTURE_REQUEST = 1;
    private String personImagePath;
    private ImageView img_personview_picture ;
    EditText txt_personview_name;
    EditText txt_personview_phoneno;
    EditText txt_personview_accountno;
    EditText txt_personview_weight;
    Button btn_personview_save;
    Button btn_personview_return;
    Button btn_personview_selectpicture;
    TextView txt_personview_pagetitle;
    TextView txt_personview_namelable;
    TextView txt_personview_phonelable;
    TextView txt_personview_accountnolable;
    TextView txt_personview_weightlable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personview);
        findViewsByIds();

        Bundle bundle = getIntent().getExtras();
        final Integer personID = Helper.GetInt(bundle.get("Id"));
        if (personID != 0) {
            Person person = new DBHelper(this).GetPerson(personID);

            txt_personview_name.setText(person.getName());
            txt_personview_phoneno.setText(person.getPhoneNo());
            txt_personview_accountno.setText(person.getAccountNo());
            txt_personview_weight.setText(person.getWeight().toString());

            if (person.getImage() != null && person.getImage().length() > 0) {
                personImagePath = person.getImage();
                Bitmap bitmap = BitmapFactory.decodeFile(person.getImage());
                img_personview_picture.setImageBitmap(bitmap);
            }
        }

        btn_personview_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DBHelper(view.getContext()).SetPersonInfo(new Person
                        (
                                personID,
                                txt_personview_name.getText().toString(),
                                txt_personview_accountno.getText().toString(),
                                txt_personview_phoneno.getText().toString(),
                                Helper.GetInt(txt_personview_weight.getText()),
                                personImagePath
                        )
                );
                finish();
            }
        });

        btn_personview_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_personview_selectpicture.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public  void onActivityResult(int requestCode,int resultCode,Intent data){
        if (resultCode == RESULT_OK){
            if (requestCode == PICTURE_REQUEST){
                Uri imagePath = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imagePath);
                    personImagePath = Helper.getRealPathFromURI(this, imagePath);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    img_personview_picture.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this,"Unalble to open file",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void findViewsByIds() {
        img_personview_picture = (ImageView) findViewById(R.id.img_personview_picture);
        txt_personview_name = (EditText) findViewById(R.id.txt_personview_name);
        txt_personview_phoneno = (EditText) findViewById(R.id.txt_personview_phoneno);
        txt_personview_accountno = (EditText) findViewById(R.id.txt_personview_accountno);
        txt_personview_weight = (EditText) findViewById(R.id.txt_personview_weight);
        txt_personview_namelable = (TextView) findViewById(R.id.txt_personview_namelable);
        txt_personview_phonelable = (TextView) findViewById(R.id.txt_personview_phonelable);;
        txt_personview_accountnolable = (TextView) findViewById(R.id.txt_personview_accountnolable);;
        txt_personview_weightlable = (TextView) findViewById(R.id.txt_personview_weightlable);;

        btn_personview_save = (Button)findViewById(R.id.btn_personview_save);
        btn_personview_return = (Button)findViewById(R.id.btn_personview_return);
        btn_personview_selectpicture = (Button) findViewById(R.id.btn_personview_selectpicture);
        txt_personview_pagetitle = (TextView) findViewById(R.id.txt_personview_pagetitle);

        Helper.setTypeFace(this,txt_personview_name);
        Helper.setTypeFace(this,txt_personview_phoneno);
        Helper.setTypeFace(this,txt_personview_accountno);
        Helper.setTypeFace(this, txt_personview_weight);
        Helper.setTypeFace(this, txt_personview_pagetitle);

        Helper.setTypeFace(this, txt_personview_namelable);
        Helper.setTypeFace(this, txt_personview_phonelable);
        Helper.setTypeFace(this, txt_personview_accountnolable);
        Helper.setTypeFace(this, txt_personview_weightlable);

        Helper.setTypeFace(this, btn_personview_save);
        Helper.setTypeFace(this, btn_personview_return);
        Helper.setTypeFace(this, btn_personview_selectpicture);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
