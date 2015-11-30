package com.example.android.camera2basic;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dolevb on 27/11/2015.
 */
public class Item {
    private int id;
    private String title;
    private String barcode;
    private Date expirationDate;

    public Item(int id, String title, Date expirationDate) {
        super();
        this.id = id;
        this.title = title;
        this.expirationDate = expirationDate;
    }

    public Item(String title, Date expirationDate) {
        super();
        this.title = title;
        this.expirationDate = expirationDate;
    }
    public String getDescription() {
        Log.i("Item", expirationDate.toString());
        return "The item expiration date is: " + android.text.format.DateFormat.format("dd/MM/yyyy", expirationDate);

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}