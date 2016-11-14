package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.edalat.android.travelcostsmanagement.R;

import Tools.Helper;

/**
 * Created by alireza on 12/09/2016.
 */
public class DatePickerDialog implements View.OnClickListener {

    TextView mTextView;
    Context mContex;

    public DatePickerDialog(Context ctx,TextView txv){
        this.mTextView = txv;
        this.mContex = ctx;
    }

    @Override
    public void onClick(View view) {
        final Dialog dialog = new Dialog(mContex);
        dialog.setContentView(R.layout.dialog_datepicker);
        dialog.setTitle(R.string.action_date);
        final NumberPicker npk_datepicker_year = (NumberPicker) dialog.findViewById(R.id.npk_datepicker_year);
        final NumberPicker npk_datepicker_month = (NumberPicker) dialog.findViewById(R.id.npk_datepicker_month);
        final NumberPicker npk_datepicker_day = (NumberPicker) dialog.findViewById(R.id.npk_datepicker_day);
        Button btn_datepicker_select = (Button) dialog.findViewById(R.id.btn_datepicker_select);
        npk_datepicker_year.setMinValue(1394);
        npk_datepicker_year.setMaxValue(1400);
        npk_datepicker_month.setMinValue(1);
        npk_datepicker_month.setMaxValue(12);
        npk_datepicker_day.setMinValue(1);
        npk_datepicker_day.setMaxValue(31);
        String currentDate = mTextView.getText().toString();
        if (!currentDate.isEmpty()){
            npk_datepicker_year.setValue(Helper.GetInt(currentDate.substring(0, 4)));
            npk_datepicker_month.setValue(Helper.GetInt(currentDate.substring(5, 7)));
            npk_datepicker_day.setValue(Helper.GetInt(currentDate.substring(8, 10)));
        }

        btn_datepicker_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText(npk_datepicker_year.getValue() + "/" + String.format("%02d", npk_datepicker_month.getValue())  + "/" + String.format("%02d", npk_datepicker_day.getValue()));
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
