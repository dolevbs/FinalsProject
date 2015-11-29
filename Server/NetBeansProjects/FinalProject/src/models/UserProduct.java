
package models;

import java.sql.Date;

public class UserProduct {

    private User user;
    private Product product;
    private String expirationDate;

    public UserProduct(User user, Product product, String expirationDate) {
        this.user = user;
        this.product = product;
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
    
}
