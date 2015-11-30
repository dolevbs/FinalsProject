package com.example.android.camera2basic;

import java.io.File;

/**
 * Created by dolevb on 30/11/2015.
 */
public class ItemRegister {
    private String barcode;
    private String itemName;
    private File expirationDateFile;

    public ItemRegister() {
        barcode = null;
        itemName = null;
        expirationDateFile = null;
    }
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public File getExpirationDateFile() {
        return expirationDateFile;
    }

    public void setExpirationDateFile(File expirationDateFile) {
        this.expirationDateFile = expirationDateFile;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
