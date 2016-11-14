package controller.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IntegerRes;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.edalat.android.travelcostsmanagement.CostDefineActivity;
import com.edalat.android.travelcostsmanagement.R;

import java.util.HashMap;
import java.util.List;

import Common.DistributionType;
import Tools.Helper;
import model.Person;

import static Tools.Helper.GetFormatedNumber;
import static Tools.Helper.GetInt;

/**
 * Created by alireza on 16/09/2016.
 */
public class PersonCostCalculationAdapter extends BaseAdapter {
    Context mContext;
    List<Person> mPersonList;
    DistributionType distributionType;
    Integer totalAmount;
    HashMap<Integer,Integer> values;
//    boolean mRecalculate;
//    boolean mCustomMode;
    TextView txt_itemperson_name;
    TextView txt_itemperson_result;
    public PersonCostCalculationAdapter(Context ctx,List<Person> personlist, DistributionType distributionType,Integer totalAmount,HashMap<Integer,Integer> values){
        this.mContext = ctx;
        this.mPersonList = personlist;
        this.distributionType = distributionType;
        this.totalAmount = totalAmount;
        this.values = values.size() != 0 ? values : calculateValues(distributionType,mPersonList,totalAmount);
//        this.mRecalculate = values.size() == 0;
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
        final View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_persons, null);
        findViewById(rootView);
        final Person person = mPersonList.get(i);
//        mCustomMode = false;
        txt_itemperson_name.setText(person.getName());
//        Integer amount = 0;
//        if (mRecalculate) {
//
//            if (distributionType == DistributionType.Proportional) {
//                Integer totalWeight = 0;
//                for (int j = 0; j < mPersonList.size(); j++)
//                    totalWeight += mPersonList.get(j).getWeight();
//                amount = totalAmount * person.getWeight() / totalWeight;
//            } else if (distributionType == DistributionType.Equal) {
//                amount = totalAmount / mPersonList.size();
//            }
//
//            if (amount != 0)
//                txt_itemperson_result.setText(amount.toString());
//            values.put(mPersonList.get(i).getId(), amount);
//        }else{
            if (values.containsKey(person.getId())) {
                txt_itemperson_result.setText(values.get(person.getId()).toString());
            }else
                txt_itemperson_result.setText("0");
//        }


        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(person.getName());

                // Set up the input
                final EditText input = new EditText(mContext);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setText(values.get(person.getId()).toString());
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton(R.string.action_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        findViewById(rootView);
                        txt_itemperson_result.setText(GetFormatedNumber(GetInt(input.getText().toString())));
                        values.put(person.getId(), GetInt(input.getText().toString()));
//                        mCustomMode = true;
                        ((CostDefineActivity) mContext).onDialogRespond();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        return rootView;
    }

    public HashMap<Integer,Integer> getValues(){
        return values;
    }
    public Integer getSum(){
        Integer result = 0;
        for (Integer key : values.keySet()) {
            result += values.get(key);
        }
        return result;
    }
    private void findViewById(View rootView){
        txt_itemperson_name = (TextView) rootView.findViewById(R.id.txt_itemperson_name);
        txt_itemperson_result = (TextView) rootView.findViewById(R.id.txt_itemperson_result);

        Helper.setTypeFace(rootView.getContext(), txt_itemperson_name);
        Helper.setTypeFace(rootView.getContext(),txt_itemperson_result);
    }
//    public boolean isCustomMode(){
//        return mCustomMode;
//    }

    private HashMap<Integer,Integer> calculateValues(DistributionType distributionType,List<Person> persons,Integer totalAmount){
        HashMap<Integer,Integer> result = new HashMap<Integer,Integer>();

        for (int i = 0;i < persons.size();i++) {
            Person person = persons.get(i);
            Integer amount = 0;
            if (distributionType == DistributionType.Proportional) {
                Integer totalWeight = 0;
                for (int j = 0; j < persons.size(); j++)
                    totalWeight += persons.get(j).getWeight();
                amount = totalAmount * person.getWeight() / totalWeight;
            } else if (distributionType == DistributionType.Equal) {
                amount = totalAmount / persons.size();
            }

//            if (amount != 0)
//                txt_itemperson_result.setText(amount.toString());
            if (amount != 0)
                result.put(person.getId(), amount);
        }
        return result;
    }
}
