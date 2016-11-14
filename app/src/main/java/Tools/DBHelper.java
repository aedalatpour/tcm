package Tools;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.BillDetails;
import model.Cost;
import model.Payment;
import model.Person;
import model.Trip;


public class DBHelper extends SQLiteOpenHelper{
    private static String DBPath;
    private static String DBName = "TCM.db";
    private SQLiteDatabase mDatabase;
    private final Context mContex;

    public  DBHelper(Context context) {
        super(context,DBName,null,3);
        mContex = context;
        DBPath = context.getDatabasePath(DBName).getParent() + File.separator;
    }

    //region Common
    public  void  createDatabase() throws IOException {
        boolean dbExists = checkDB()  ;
        if (dbExists){} else {
            this.getReadableDatabase();
            copyDatabase();
        }
    }
    private boolean checkDB(){
        SQLiteDatabase database =null;
        try{
            String myPath = DBPath + DBName;
            database = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        } catch (SQLiteException ex){

        }
        if (database != null)
            database.close();

        return database != null ? true:false;

    }
    private void copyDatabase() throws IOException{
        InputStream myInput = mContex.getAssets().open(DBName);
        String outFileName = DBPath + DBName;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int lenght;
        while ((lenght = myInput.read(buffer)) > 0){
            myOutput.write(buffer,0,lenght);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        if (i == 1 && i1 == 2){
//            sqLiteDatabase.execSQL("ALTER TABLE TripCosts ADD PayerPersonId INT NULL");
//            onCreate(sqLiteDatabase);
//        }else if (i == 2 && i1 == 3){
//            sqLiteDatabase.execSQL("CREATE TABLE TripPayments (Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                    "TripId INTEGER," +
//                    "Title TEXT," +
//                    "Date TEXT," +
//                    "Amount INTEGER," +
//                    "PayerPersonId INTEGER," +
//                    "ReceiverPersonId INTEGER); ");
//            onCreate(sqLiteDatabase);
//        }
    }
    //endregion

    //region Persons
    private static Integer PERSON_ID = 0;
    private static Integer PERSON_NAME = 1;
    private static Integer PERSON_AccountNo = 2;
    private static Integer PERSON_Picture = 3;
    private static Integer PERSON_PhoneNo = 4;
    private static Integer PERSON_Weight = 5;

    public List<Person> GetAllPersons(){
        List<Person> result = new ArrayList<Person>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM person",null);
        if (cursor.moveToFirst()){
            do{
                Person person = new Person();
                person.setId(cursor.getInt(PERSON_ID));
                person.setName(cursor.getString(PERSON_NAME));
                person.setAccountNo(cursor.getString(PERSON_AccountNo));
                person.setImage(cursor.getString(PERSON_Picture));
                person.setPhoneNo(cursor.getString(PERSON_PhoneNo));
                person.setWeight(cursor.getInt(PERSON_Weight));

                result.add(person);

            } while (cursor.moveToNext());
        }
        return result;
    }
    public List<Person> GetPersons(List<Integer> ids){
        List<Person> result = new ArrayList<Person>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM person",null);
        if (cursor.moveToFirst()){
            do{
                if (ids.contains(cursor.getInt(PERSON_ID))) {
                    Person person = new Person();
                    person.setId(cursor.getInt(PERSON_ID));
                    person.setName(cursor.getString(PERSON_NAME));
                    person.setAccountNo(cursor.getString(PERSON_AccountNo));
                    person.setImage(cursor.getString(PERSON_Picture));
                    person.setPhoneNo(cursor.getString(PERSON_PhoneNo));
                    person.setWeight(cursor.getInt(PERSON_Weight));

                    result.add(person);
                }
            } while (cursor.moveToNext());
        }
        return result;
    }
    public List<Person> searchPersons(String term){
        List<Person> result = new ArrayList<Person>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM person WHERE Name LIKE ? ",new String[] {"%" + term + "%"});
        if (cursor.moveToFirst()){
            do{
                Person person = new Person();
                person.setId(cursor.getInt(PERSON_ID));
                person.setName(cursor.getString(PERSON_NAME));
                person.setAccountNo(cursor.getString(PERSON_AccountNo));
                person.setImage(cursor.getString(PERSON_Picture));
                person.setPhoneNo(cursor.getString(PERSON_PhoneNo));
                person.setWeight(cursor.getInt(PERSON_Weight));

                result.add(person);

            } while (cursor.moveToNext());
        }
        return result;
    }
    public Person GetPerson(Integer Id){
        Person person = new Person();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM person WHERE Id = ?",new String[]{Id.toString()});
        if (cursor.moveToFirst()){
                person.setId(cursor.getInt(PERSON_ID));
                person.setName(cursor.getString(PERSON_NAME));
                person.setAccountNo(cursor.getString(PERSON_AccountNo));
                person.setImage(cursor.getString(PERSON_Picture));
                person.setPhoneNo(cursor.getString(PERSON_PhoneNo));
                person.setWeight(cursor.getInt(PERSON_Weight));
            }
        return person;
    }
    public void SetPersonInfo(Person person){
        SQLiteDatabase db = getReadableDatabase();
        if (person.getId() == 0) {
            db.execSQL("INSERT INTO person (Name,PhoneNo,AccountNo,Weight,Picture) " +
                    "VALUES(?,?,?,?,?)", new String[]
                    {
                            person.getName(),
                            person.getPhoneNo(),
                            person.getAccountNo(),
                            person.getWeight().toString(),
                            person.getImage()
                    });
        }else {
            db.execSQL("UPDATE person SET Name = ?,PhoneNo = ?,AccountNo = ?,Weight = ?,Picture = ? " +
                    "WHERE Id = ?", new String[]
                    {
                            person.getName(),
                            person.getPhoneNo(),
                            person.getAccountNo(),
                            person.getWeight().toString(),
                            person.getImage(),
                            person.getId().toString()
                    });
        }
    }
    public List<Person> GetPersons(Integer tripId){
        List<Integer> relatedPersons = getTripPersons(tripId);
        List<String> result = new ArrayList<String>();
        return GetPersons(relatedPersons);
    }
    //endregion

    //region Trips
    private static Integer TRIP_ID = 0;
    private static Integer TRIP_Title = 1;
    private static Integer TRIP_Picture = 2;
    private static Integer TRIP_Date = 3;
    private static Integer TRIPPERSONS_ID = 0;
    private static Integer TRIPPERSONS_TripID = 1;
    private static Integer TRIPPERSONS_PersonID = 2;
    private static Integer TRIPPERSONS_TotalAmount = 3;

    public List<Trip> GetAllTrips(){
        List<Trip> result = new ArrayList<Trip>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Trip", null);
        if (cursor.moveToFirst()){
            do{
                Trip trip = new Trip();
                trip.setId(cursor.getInt(TRIP_ID));
                trip.setTitle(cursor.getString(TRIP_Title));
                trip.setDate(cursor.getString(TRIP_Date));
                trip.setImagePath(cursor.getString(TRIP_Picture));

                result.add(trip);

            } while (cursor.moveToNext());
        }
        return result;
    }
    public void setTripInfo(Trip trip){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("Title",trip.getTitle());
        values.put("Picture",trip.getImagePath());
        values.put("Date", trip.getDate());

        if (trip.getId() == 0) {
            long newID = db.insert("Trip", null, values);

            for (int i = 0;i <trip.getPersons().size();i++)
                db.execSQL("INSERT INTO TripPersons(TripID,PersonID,TotalAmount) VALUES(?,?,0)",new String[]{ newID + "", trip.getPersons().get(i).toString()});

        }else {
            db.update("Trip", values, "ID = ?", new String[]{trip.getId().toString()});
            List<Integer> currentPersons = getTripPersons(trip.getId());
            for (int i = 0;i < trip.getPersons().size();i++){
                if (!currentPersons.contains(trip.getPersons().get(i)))
                    db.execSQL("INSERT INTO TripPersons(TripID,PersonID,TotalAmount) VALUES(?,?,0)",new String[]{ trip.getId().toString() , trip.getPersons().get(i).toString()});
            }
            for (int i = 0;i < currentPersons.size();i++){
                if (!trip.getPersons().contains(currentPersons.get(i)))
                    db.delete("TripPersons","TripID = ? AND PersonID = ?",new String[]{trip.getId().toString(),currentPersons.get(i).toString()});
            }
        }
    }
    public Trip getTrip(Integer Id){
        Trip trip = new Trip();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Trip WHERE Id = ?",new String[]{Id.toString()});
        if (cursor.moveToFirst()){
            trip.setId(cursor.getInt(0));
            trip.setTitle(cursor.getString(1));
            trip.setImagePath(cursor.getString(2));
            trip.setDate(cursor.getString(3));
        }
        List<Integer> personIDs = new ArrayList<Integer>();
        cursor = db.rawQuery("SELECT * FROM TripPersons WHERE TripID = ?",new String[]{Id.toString()});
        if (cursor.moveToFirst()){
            do{
                personIDs.add(cursor.getInt(2));
            } while (cursor.moveToNext());
        }

        trip.setPersons(personIDs);

        return trip;
    }
    public List<Integer> getTripPersons(Integer tripID){
        List<Integer> result = new ArrayList<Integer>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TripPersons WHERE TripID = ?",new String[]{tripID.toString()});
        if (cursor.moveToFirst()){
            do{
                result.add(cursor.getInt(TRIPPERSONS_PersonID));
            } while (cursor.moveToNext());
        }
        return result;
    }
    //endregion

    //region Costs
    private static Integer TRIPCOSTS_ID = 0;
    private static Integer TRIPCOSTS_TripID = 1;
    private static Integer TRIPCOSTS_Description = 2;
    private static Integer TRIPCOSTS_Date = 3;
    private static Integer TRIPCOSTS_TotalAmount = 4;
    private static Integer TRIPCOSTS_PayerPersonId = 5;

    private static Integer TRIPCOSTDETAILS_ID = 0;
    private static Integer TRIPCOSTDETAILS_TripCostID = 1;
    private static Integer TRIPCOSTDETAILS_PersonID = 2;
    private static Integer TRIPCOSTDETAILS_Amount = 3;


    private static Integer TRIPCOSTS_Distributiontype = 6;

    public List<Cost> getAllCosts(Integer tripId){
        List<Cost> result = new ArrayList<Cost>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TripCosts WHERE TripID = ?", new String[]{tripId.toString()});
        if (cursor.moveToFirst()){
            do{
                Cost cost = new Cost();
                cost.setId(cursor.getInt(TRIPCOSTS_ID));
                cost.setTripId(cursor.getInt(TRIPCOSTS_TripID));
                cost.setTitle(cursor.getString(TRIPCOSTS_Description));
                cost.setDate(cursor.getString(TRIPCOSTS_Date));
                cost.setTotalAmount(Helper.GetInt(cursor.getString(TRIPCOSTS_TotalAmount)));
                cost.setPayerPersonId(Helper.GetInt(cursor.getString(TRIPCOSTS_PayerPersonId)));
                cost.setDistributiontype(Helper.GetInt(cursor.getString(TRIPCOSTS_Distributiontype)));
                result.add(cost);

            } while (cursor.moveToNext());
        }
        return result;
    }
    public void setCostInfo(Cost cost){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("Description",cost.getTitle());
        values.put("Date",cost.getDate());
        values.put("TotalAmount", cost.getTotalAmount());
        values.put("tripId", cost.getTripId());
        values.put("payerPersonId", cost.getPayerPersonId());
        values.put("distributiontype", cost.getDistributiontype());

        if (cost.getId() == 0) {
            long newID = db.insert("TripCosts", null, values);

            for (Integer key : cost.getCostDetails().keySet())
                db.execSQL("INSERT INTO TripCostDetails(TripCostId,PersonID,Amount) VALUES(?,?,?)",new String[]{ newID + "", key.toString(),cost.getCostDetails().get(key).toString()});

        }else {
            db.update("TripCosts", values, "ID = ?", new String[]{cost.getId().toString()});
            HashMap<Integer,Integer> currentCostDetails = getCostDetails(cost.getId());
            for (Integer key : cost.getCostDetails().keySet()){
                if (!currentCostDetails.containsKey(key))
                    db.execSQL("INSERT INTO TripCostDetails(TripCostId,PersonID,Amount) VALUES(?,?,?)",new String[]{ cost.getId().toString() , key.toString(),cost.getCostDetails().get(key).toString()});
                else
                    db.execSQL("UPDATE TripCostDetails SET Amount = ? WHERE TripCostID = ? AND PersonID = ?",new String[]{cost.getCostDetails().get(key).toString(), cost.getId().toString() , key.toString()});
            }
            for (Integer key : currentCostDetails.keySet()){
                if (!cost.getCostDetails().containsKey(key))
                    db.delete("TripCostDetails","TripCostID = ? AND PersonID = ?",new String[]{cost.getId().toString(),currentCostDetails.get(key).toString()});
            }
        }
    }
    public Cost GetCost(Integer Id){
        Cost cost = new Cost();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tripCosts WHERE Id = ?",new String[]{Id.toString()});
        if (cursor.moveToFirst()){
            cost.setId(cursor.getInt(TRIPCOSTS_ID));
            cost.setTripId(cursor.getInt(TRIPCOSTS_TripID));
            cost.setTitle(cursor.getString(TRIPCOSTS_Description));
            cost.setDate(cursor.getString(TRIPCOSTS_Date));
            cost.setTotalAmount(Helper.GetInt(cursor.getString(TRIPCOSTS_TotalAmount)));
            cost.setPayerPersonId(Helper.GetInt(cursor.getString(TRIPCOSTS_PayerPersonId)));
            cost.setDistributiontype(Helper.GetInt(cursor.getString(TRIPCOSTS_Distributiontype)));
        }
        return cost;
    }
    public HashMap<Integer,Integer> getCostDetails(Integer costId){
        HashMap<Integer,Integer> result = new HashMap<Integer,Integer>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TripCostDetails WHERE TripCostID = ?", new String[]{costId.toString()});
        if (cursor.moveToFirst()){
            do{
                result.put(cursor.getInt(TRIPCOSTDETAILS_PersonID), cursor.getInt(TRIPCOSTDETAILS_Amount));

            } while (cursor.moveToNext());
        }
        return result;
    }
    //endregion

    //region Payments
    private static Integer TRIPPAYMENTS_ID = 0;
    private static Integer TRIPPAYMENTS_TripId = 1;
    private static Integer TRIPPAYMENTS_Title = 2;
    private static Integer TRIPPAYMENTS_Date = 3;
    private static Integer TRIPPAYMENTS_Amount = 4;
    private static Integer TRIPPAYMENTS_PayerPersonId = 5;
    private static Integer TRIPPAYMENTS_ReceiverPersonId = 6;

    public List<Payment> getAllPayments(Integer tripId){
        List<Payment> result = new ArrayList<Payment>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TripPayments WHERE TripID = ?", new String[]{tripId.toString()});
        if (cursor.moveToFirst()){
            do{
                Payment payment = new Payment();
                payment.setId(cursor.getInt(TRIPPAYMENTS_ID));
                payment.setTripId(cursor.getInt(TRIPPAYMENTS_TripId));
                payment.setTitle(cursor.getString(TRIPPAYMENTS_Title));
                payment.setDate(cursor.getString(TRIPPAYMENTS_Date));
                payment.setAmount(Helper.GetInt(cursor.getString(TRIPPAYMENTS_Amount)));
                payment.setPayerPersonId(Helper.GetInt(cursor.getString(TRIPPAYMENTS_PayerPersonId)));
                payment.setReceiverPersonId(Helper.GetInt(cursor.getString(TRIPPAYMENTS_ReceiverPersonId)));

                result.add(payment);

            } while (cursor.moveToNext());
        }
        return result;
    }
    public void setPaymentInfo(Payment payment){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("Title",payment.getTitle());
        values.put("Date",payment.getDate());
        values.put("Amount", payment.getAmount());
        values.put("TripId", payment.getTripId());
        values.put("PayerPersonId", payment.getPayerPersonId());
        values.put("ReceiverPersonId", payment.getReceiverPersonId());

        if (payment.getId() == 0) {
            long newID = db.insert("TripPayments", null, values);
        }else {
            db.update("TripPayments", values, "ID = ?", new String[]{payment.getId().toString()});
        }
    }
    public Payment GetPayment(Integer Id){
        Payment payment = new Payment();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TripPayments WHERE Id = ?",new String[]{Id.toString()});
        if (cursor.moveToFirst()){
            payment.setId(cursor.getInt(TRIPPAYMENTS_ID));
            payment.setTripId(cursor.getInt(TRIPPAYMENTS_TripId));
            payment.setTitle(cursor.getString(TRIPPAYMENTS_Title));
            payment.setDate(cursor.getString(TRIPPAYMENTS_Date));
            payment.setAmount(Helper.GetInt(cursor.getString(TRIPPAYMENTS_Amount)));
            payment.setPayerPersonId(Helper.GetInt(cursor.getString(TRIPPAYMENTS_PayerPersonId)));
            payment.setReceiverPersonId(Helper.GetInt(cursor.getString(TRIPPAYMENTS_ReceiverPersonId)));
        }
        return payment;
    }
    //endregion

    //region Bill

    public Integer getSummary(Integer tripId, Integer personId){
        Integer result = 0;
        List<BillDetails> bills = getBillDetails(tripId,personId);
        for (int i =0;i< bills.size();i++){
            result += bills.get(i).getCostAmount();
            result -= bills.get(i).getPaymentAmount();
        }
        return  result;
    }
    public List<BillDetails> getBillDetails(Integer tripId,Integer personId){
        List<BillDetails> result = new ArrayList<BillDetails>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT costs.Description,det.Amount FROM TripCosts costs INNER JOIN TripCostDetails det ON det.TripCostID = costs.ID WHERE costs.TripID = ? AND det.PersonID = ? "
                , new String[] {tripId.toString(),personId.toString()});
        if (cursor.moveToFirst()){
            do{
                BillDetails billDetails = new BillDetails();
                billDetails.setTitle(cursor.getString(0));
                billDetails.setCostAmount(cursor.getInt(1));
                billDetails.setPaymentAmount(0);
                result.add(billDetails);

            } while (cursor.moveToNext());
        }

        cursor = db.rawQuery("SELECT Title,Amount FROM TripPayments payments WHERE TripID = ? AND ReceiverPersonId = ? "
                , new String[] {tripId.toString(),personId.toString()});
        if (cursor.moveToFirst()){
            do{
                BillDetails billDetails = new BillDetails();
                billDetails.setTitle(cursor.getString(0));
                billDetails.setCostAmount(cursor.getInt(1));
                billDetails.setPaymentAmount(0);
                result.add(billDetails);

            } while (cursor.moveToNext());
        }

        cursor = db.rawQuery("SELECT costs.Description,costs.TotalAmount FROM TripCosts costs WHERE costs.TripID = ? AND costs.PayerPersonId = ? "
                , new String[] {tripId.toString(),personId.toString()});
        if (cursor.moveToFirst()){
            do{
                BillDetails billDetails = new BillDetails();
                billDetails.setTitle(cursor.getString(0));
                billDetails.setCostAmount(0);
                billDetails.setPaymentAmount(cursor.getInt(1));
                result.add(billDetails);

            } while (cursor.moveToNext());
        }

        cursor = db.rawQuery("SELECT Title,Amount FROM TripPayments payments WHERE TripID = ? AND PayerPersonId = ? "
                , new String[] {tripId.toString(),personId.toString()});
        if (cursor.moveToFirst()){
            do{
                BillDetails billDetails = new BillDetails();
                billDetails.setTitle(cursor.getString(0));
                billDetails.setCostAmount(0);
                billDetails.setPaymentAmount(cursor.getInt(1));
                result.add(billDetails);

            } while (cursor.moveToNext());
        }


        return  result;
    }
    //endregion
}