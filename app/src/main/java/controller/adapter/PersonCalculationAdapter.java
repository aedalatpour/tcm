package controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edalat.android.travelcostsmanagement.BillActivity;
import com.edalat.android.travelcostsmanagement.R;

import java.util.List;

import Tools.DBHelper;
import Tools.Helper;
import model.Person;

import static Tools.Helper.GetFormatedNumber;

/**
 * Created by alireza on 15/09/2016.
 */
public class PersonCalculationAdapter extends BaseAdapter {
    Context mContext;
    List<Person> mPersonList;
    Integer mTripId;
    TextView txt_itemperson_name;
    TextView txt_itemperson_result;

    public PersonCalculationAdapter(Context ctx,List<Person> personlist,Integer tripId){
        this.mContext = ctx;
        this.mPersonList = personlist;
        this.mTripId = tripId;
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
        return mPersonList.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_persons, null);
        findViewById(rootView);

        DBHelper dbHelper = new DBHelper(rootView.getContext());
        txt_itemperson_name.setText(mPersonList.get(i).getName());
        txt_itemperson_result.setText(GetFormatedNumber(dbHelper.getSummary(mTripId,mPersonList.get(i).getId())));

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BillActivity.class);
                intent.putExtra("personId", mPersonList.get(i).getId().toString());
                intent.putExtra("tripId", mTripId.toString());
                mContext.startActivity(intent);
            }
        });

        return rootView;
    }
    private void findViewById(View rootView){
        txt_itemperson_name = (TextView) rootView.findViewById(R.id.txt_itemperson_name);
        txt_itemperson_result = (TextView) rootView.findViewById(R.id.txt_itemperson_result);

        Helper.setTypeFace(rootView.getContext(), txt_itemperson_name);
        Helper.setTypeFace(rootView.getContext(),txt_itemperson_result);
    }

}
