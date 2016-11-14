package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alireza on 10/09/2016.
 */
public class Trip {
    Integer id;
    String title;
    String date;
    String imagePath;
    List<Integer> persons;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Integer> getPersons() {
        if (persons != null)
            return persons;
        else
            return new ArrayList<Integer>();
    }

    public void setPersons(List<Integer> persons) {
        this.persons = persons;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
