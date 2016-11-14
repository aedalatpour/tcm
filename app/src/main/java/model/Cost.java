package model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by alireza on 15/09/2016.
 */
public class Cost {
    Integer Id;
    Integer tripId;
    String title;
    String date;
    Integer totalAmount;
    Integer payerPersonId;
    Integer distributiontype;

    public Integer getDistributiontype() {
        return distributiontype;
    }

    public void setDistributiontype(Integer distributiontype) {
        this.distributiontype = distributiontype;
    }


    HashMap<Integer,Integer> costDetails;

    public Integer getPayerPersonId() {
        return payerPersonId;
    }

    public void setPayerPersonId(Integer payerPersonId) {
        this.payerPersonId = payerPersonId;
    }

    public HashMap<Integer,Integer> getCostDetails() {
        return costDetails;
    }

    public void setCostDetails(HashMap<Integer,Integer> costDetails) {
        this.costDetails = costDetails;
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

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }


}
