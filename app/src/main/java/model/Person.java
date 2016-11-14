package model;

import android.database.sqlite.SQLiteDatabase;
import android.media.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alireza on 01/09/2016.
 */
public class Person {
    Integer Id;
    String name;
    String accountNo;
    String phoneNo;
    Integer weight;
    String imagePath;

    public  Person()
    {

    }

    public  Person(Integer id, String name,String accountNo,String phoneNo,Integer weight,String imagePath)
    {
        this.Id = id;
        this.name = name;
        this.accountNo = accountNo;
        this.phoneNo = phoneNo;
        this.weight = weight;
        this.imagePath = imagePath;
    }
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setImage(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {

        return name;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getImage() {
        return imagePath;
    }

}
