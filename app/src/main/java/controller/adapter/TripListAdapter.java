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

import com.edalat.android.travelcostsmanagement.R;
import com.edalat.android.travelcostsmanagement.TripDetailsActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import Tools.Helper;
import Tools.RoundedCornerImageView;
import model.Trip;

/**
 * Created by alireza on 10/09/2016.
 */
public class TripListAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<Trip> mTripList;

    CheckBox chk_item_select;
    TextView txtTitle;
    TextView txtSmallTitle;
    RoundedCornerImageView imgvPicture;
    public TripListAdapter(Context ctx,List<Trip> trips)
    {
        mContext = ctx;
        mTripList = trips;
    }

    @Override
    public int getCount() {
        return mTripList.size();
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

        final String title = mTripList.get(i).getTitle();
        final String smallTitle = mTripList.get(i).getDate();
        String imagePath = mTripList.get(i).getImagePath();
        try {
            imagePath = imagePath == null ? "" : imagePath;

            InputStream inputStream = new FileInputStream(imagePath);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imgvPicture.setImageBitmap(bitmap);
            imgvPicture.setRadius(100);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        txtTitle.setText(title);
        txtSmallTitle.setText(smallTitle);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TripDetailsActivity.class);
                intent.putExtra("Id", mTripList.get(i).getId().toString());
                mContext.startActivity(intent);
            }
        });
        txtTitle.setTypeface(Helper.getTypeFace(mContext));
        txtSmallTitle.setTypeface(Helper.getTypeFace(mContext));
        chk_item_select.setVisibility(View.INVISIBLE);
        return rootView;
    }
    public  void findViewById(View rootView){
        chk_item_select = (CheckBox) rootView.findViewById(R.id.chk_item_select);
        txtTitle = (TextView)rootView.findViewById(R.id.txtTitle);
        txtSmallTitle = (TextView)rootView.findViewById(R.id.txtSmallTitle);
        imgvPicture = (RoundedCornerImageView) rootView.findViewById(R.id.imgvPicture);

        Helper.setTypeFace(rootView.getContext(), txtTitle);
        Helper.setTypeFace(rootView.getContext(), txtSmallTitle);
    }
}
