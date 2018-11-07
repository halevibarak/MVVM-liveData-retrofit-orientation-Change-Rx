package com.test.halevi.barakapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Barak on 24/08/2017.
 */

public class ContactResponse
{
    @SerializedName("contacts")
    List<Contact> contacts;

    public ContactResponse(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}


