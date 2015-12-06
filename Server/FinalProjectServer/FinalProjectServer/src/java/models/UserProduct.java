
package models;

import java.sql.Date;

public class UserProduct {

    private int id;
    private String name;
    private String expirationDate;

    public UserProduct(int id, String name, String expirationDate) {
        this.id = id;
        this.name = name;
        this.expirationDate = expirationDate;
    }

    public int getUser() {
        return id;
    }

    public String getProduct() {
        return name;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
    
}
