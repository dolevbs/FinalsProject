/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Blob;

public class Product {

    private int id;
    private String name;
    private Blob image;
    private String barcode;

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Product(int id, String name, String barcode) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
    }

    public Product(int id, String name, Blob image, String barcode) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.barcode = barcode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Blob getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getBarcode() {
        return barcode;
    }
}
