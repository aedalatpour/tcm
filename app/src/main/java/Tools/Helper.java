package Tools;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by alireza on 02/09/2016.
 */
public class Helper {
    public static Integer GetInt(Object obj)
    {
        Integer result = 0;
        try {

            return Integer.parseInt(obj.toString());
        }
        catch (Exception ex){

        }
        return result;
    }
    public static String GetFormatedNumber(Integer number){
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }
    public  static String GetCurrentJalaiDate(){
        Calendar now = Calendar.getInstance();
        return JalaliCalendar.gregorianToJalali(new JalaliCalendar.YearMonthDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH ) + 1, now.get(Calendar.DATE))).toString();
    }
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public  static Typeface getTypeFace(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/Vazir.ttf");
    }

    public static void setTypeFace(Context context,TextView txtView){
        txtView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Vazir.ttf"));
    }
    public static void setTypeFaceBold(Context context,TextView txtView){
        txtView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Vazir-Bold.ttf"));
    }
    public static void setTypeFace(Context context,Button btn){
        btn.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Vazir.ttf"));
    }


}
