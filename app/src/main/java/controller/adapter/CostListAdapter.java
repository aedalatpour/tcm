package controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edalat.android.travelcostsmanagement.CostDefineActivity;
import com.edalat.android.travelcostsmanagement.R;

import java.util.List;

import Tools.Helper;
import model.Cost;

import static Tools.Helper.GetFormatedNumber;

/**
 * Created by alireza on 15/09/2016.
 */
public class CostListAdapter extends BaseAdapter {
    Context mContext;
    List<Cost> mCostList;

    TextView txt_itemcost_title ;
    TextView txt_itemcost_smalltitle ;
    TextView txt_itemcost_amount ;

    public CostListAdapter(Context ctx,List<Cost> costs){
        this.mContext = ctx;
        this.mCostList = costs;

    }
    @Override
    public int getCount() {
        return mCostList.size();
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

        Cost cost = mCostList.get(i);

        txt_itemcost_title.setText(cost.getTitle());
        txt_itemcost_smalltitle.setText(cost.getDate());
        txt_itemcost_amount.setText(GetFormatedNumber(cost.getTotalAmount()));

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CostDefineActivity.class);
                intent.putExtra("Id", mCostList.get(i).getId().toString());
                intent.putExtra("tripId", mCostList.get(i).getTripId().toString());
                mContext.startActivity(intent);
            }
        });


        return rootView;
    }
    private void findViewById(View rootView){
        txt_itemcost_title = (TextView) rootView.findViewById(R.id.txt_itemcost_title);
        txt_itemcost_smalltitle = (TextView) rootView.findViewById(R.id.txt_itemcost_smalltitle);
        txt_itemcost_amount = (TextView) rootView.findViewById(R.id.txt_itemcost_amount);

        Helper.setTypeFace(rootView.getContext(),txt_itemcost_title);
        Helper.setTypeFace(rootView.getContext(),txt_itemcost_smalltitle);
        Helper.setTypeFace(rootView.getContext(),txt_itemcost_amount);

    }
}
