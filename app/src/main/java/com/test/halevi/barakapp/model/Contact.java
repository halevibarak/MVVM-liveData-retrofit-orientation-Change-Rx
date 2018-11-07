package com.test.halevi.barakapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

/**
 * Created by Barak on 24/08/2017.
 */

public class Contact implements Comparator<Contact>
{
    @SerializedName("name")
    String name;
    @SerializedName("id")
    String id;

    public Contact(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public Contact() {
    }

    public int compare(Contact left, Contact right) {
        return left.name.compareTo(right.name);
    }
    public String getName() {
        return name;
    }


    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setId(String id) {
        this.id = id;
    }

}


