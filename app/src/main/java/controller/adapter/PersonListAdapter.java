package controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.edalat.android.travelcostsmanagement.PersonDefineActivity;
import com.edalat.android.travelcostsmanagement.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Tools.Helper;
import Tools.RoundedCornerImageView;
import model.Person;

/**
 * Created by alireza on 01/09/2016.
 */
public class PersonListAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<Person> mPersonList;
    private final boolean mMultiselectMode;

    CheckBox chk_item_select;
    TextView txtTitle;
    TextView txtSmallTitle;
    RoundedCornerImageView imgvPicture;

    public List<Integer> getSelectedItems() {
        return mSelectedItems;
    }

    private List<Integer> mSelectedItems;
    public  PersonListAdapter(Context context,List<Person> personList,boolean multiselectMode,List<Integer> selectedItems)
    {
        this.mContext = context;
        this.mPersonList = personList;
        this.mMultiselectMode = multiselectMode;
        this.mSelectedItems = selectedItems;
    }
    @Override
    public int getCount() {
        return mPersonList.size();
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
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_mainlistview, null);
        findViewById(rootView);

        final String name = mPersonList.get(i).getName();
        final String phoneNo = mPersonList.get(i).getPhoneNo();
        String imagePath = mPersonList.get(i).getImage();
        try {
            imagePath = imagePath == null ? "" : imagePath;

            InputStream inputStream = new FileInputStream(imagePath);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imgvPicture.setRadius(100);
            imgvPicture.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        txtTitle.setText(name);
        txtSmallTitle.setText(phoneNo);
        if (mMultiselectMode){
            chk_item_select.setChecked(mSelectedItems.contains(mPersonList.get(i).getId()));
        }else {
            chk_item_select.setVisibility(View.INVISIBLE);
        }
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mMultiselectMode) {
                    Intent intent = new Intent(mContext, PersonDefineActivity.class);
                    intent.putExtra("Id", mPersonList.get(i).getId().toString());
                    mContext.startActivity(intent);

                } else {
                    findViewById(view);
                    if (mSelectedItems.contains(mPersonList.get(i).getId())) {
                        mSelectedItems.remove(mPersonList.get(i).getId());
                        chk_item_select.setChecked(false);
                    } else {
                        mSelectedItems.add(mPersonList.get(i).getId());
                        chk_item_select.setChecked(true);
                    }
                }
            }
        });

        return rootView;
    }
    private void findViewById(View rootView){
        chk_item_select = (CheckBox) rootView.findViewById(R.id.chk_item_select);
        txtTitle = (TextView)rootView.findViewById(R.id.txtTitle);
        txtSmallTitle = (TextView)rootView.findViewById(R.id.txtSmallTitle);
        imgvPicture = (RoundedCornerImageView) rootView.findViewById(R.id.imgvPicture);


        Helper.setTypeFace(rootView.getContext(), txtTitle);
        Helper.setTypeFace(rootView.getContext(),txtSmallTitle);
    }
}
