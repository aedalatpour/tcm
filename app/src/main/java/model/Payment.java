package model;

import java.util.HashMap;

/**
 * Created by alireza on 15/09/2016.
 */
public class Payment {
    Integer Id;
    Integer tripId;
    String title;
    String date;
    Integer amount;
    Integer payerPersonId;
    Integer receiverPersonId;

    public Integer getReceiverPersonId() {
        return receiverPersonId;
    }

    public void setReceiverPersonId(Integer receiverPersonId) {
        this.receiverPersonId = receiverPersonId;
    }

    public Integer getPayerPersonId() {
        return payerPersonId;
    }

    public void setPayerPersonId(Integer payerPersonId) {
        this.payerPersonId = payerPersonId;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


}
