/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import models.Product;
import models.User;
import models.UserProduct;

public class DbManager {

    public static String urlCn = "jdbc:derby://localhost:1527/finalproject";
    public static Connection connection;

    public DbManager() throws SQLException, ClassNotFoundException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        connection = DriverManager.getConnection(urlCn, "root", "root");
    }

    public String getProductList() throws Exception {
        String jsonObject;
        ArrayList<Product> productList = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM PRODUCT");
        while (rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getString("NAME");
            String barcode = rs.getString("BARCODE");
            productList.add(new Product(id, name, barcode));
        }
        jsonObject = new Gson().toJson(productList);
        return jsonObject;
    }

    public String getProductByBarcode(String barcode) throws Exception {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT NAME FROM PRODUCT WHERE BARCODE LIKE '" + barcode + "'");
        return rs.next() ? rs.getString("NAME") : null;
    }
    
        public int getProductIdByBarcode(String barcode) throws Exception {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT ID FROM PRODUCT"
                + "                     WHERE BARCODE LIKE '" + barcode + "'");
        return rs.next() ? rs.getInt("ID") : -1;
    }
        
    public String getProductJsonByBarcode(String barcode) throws Exception {
        String jsonObject;
        Product product = null;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM PRODUCT"
                + "                     WHERE BARCODE LIKE '" + barcode + "'");
        while (rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getString("NAME");
            product = new Product(id, name, barcode);
        }
        jsonObject = new Gson().toJson(product);
        return jsonObject;
    }

    public String getUserList() throws Exception {
        String jsonObject;
        ArrayList<User> userList = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("select * from USERS");
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            userList.add(new User(id, name));
        }
        jsonObject = new Gson().toJson(userList);
        return jsonObject;
    }

    public String getUserProductList(int id) throws Exception {
        String jsonObject;
        ArrayList<UserProduct> userProductList = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT  U.ID AS USER_ID,"
                + "                             U.NAME AS USER_NAME,"
                + "                             P.ID AS PRODUCT_ID,"
                + "                             P.NAME AS PRODUCT_NAME,"
                + "                             PU.EXPIRE_DATE, "
                + "                             PU.ID AS ID"
                + "                     FROM USERS U, PRODUCT P, USER_PRODUCTS PU"
                + "                     WHERE U.ID=PU.USER_ID AND"
                + "                             P.ID=PU.PRODUCT_ID AND"
                + "                             U.ID=" + id);
        while (rs.next()) {
            int puId = rs.getInt("ID");
            String productName = rs.getString("PRODUCT_NAME");
            java.sql.Date expireDate = rs.getDate(5);
            DateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            userProductList.add(new UserProduct(puId, productName, sdf.format(expireDate)));
        }
        jsonObject = new Gson().toJson(userProductList);
        return jsonObject;
    }

    public boolean verifyUserAndPassword(String user, String password) throws Exception {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT PASSWORD FROM USERS"
                + "                     WHERE USER_NAME LIKE '" + user + "'");
        while (rs.next()) {
            String userPassword = rs.getString("PASSWORD");
            if (password.equals(userPassword)) {
                return true;
            }
        }
        return false;
    }
    
    public int getUserIdByName(String user) throws Exception {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT PASSWORD FROM USERS"
                + "                     WHERE USER_NAME LIKE '" + user + "'");
        return rs.next() ? rs.getInt("ID") : -1;
    }

    public boolean verifyUserExist(int userId) throws Exception {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT PASSWORD FROM USERS"
                + "                     WHERE ID=" + userId);
        return rs.next();
    }
    
    public void addProductUser (int userId, int productId, Date expireDate) throws Exception {
        java.sql.Date sqlExpireDate = new java.sql.Date(expireDate.getTime());
        PreparedStatement pst = connection.prepareStatement("INSERT INTO USER_PRODUCTS (USER_ID,PRODUCT_ID,EXPIRE_DATE)"
                + "                                          VALUES (?,?,?)");
            pst.setInt(1, userId);
            pst.setInt(2, productId);
            pst.setDate(3, sqlExpireDate);
            pst.executeUpdate();
    }

}
